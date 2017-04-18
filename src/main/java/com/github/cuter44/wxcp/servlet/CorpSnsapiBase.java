package com.github.cuter44.wxcp.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;
import java.net.URLEncoder;

//import org.apache.http.client.*;
import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxcp.*;
import com.github.cuter44.wxcp.reqs.*;
import com.github.cuter44.wxcp.resps.*;
import com.github.cuter44.wxcp.util.*;

/** 企业应用中的URL链接（包括自定义菜单或者消息中的链接），可以通过OAuth2.0验证接口来获取成员的身份信息。
 *
 * 关于该 servlet 的工作流程请参见 <a href="http://qydev.weixin.qq.com/wiki/index.php?title=OAuth%E9%AA%8C%E8%AF%81%E6%8E%A5%E5%8F%A3">OAuth2.0验证接口说明↗</a>
 *
 * 需要在微信客户端上执行
 *
 * <b>直至微信6.0为止都无法使用 Ajax 调用此方法</b>, 该尿性是由微信客户端造成的.
 *
 * <pre style="font-size:12px">

    GET /corp-snsapi-base.api
    取得 openid

    <strong>参数</strong>
    <i>code    :string , 从 open.weixin.qq.com 跳转时带入, 无需客户端处理.</i>
    redir   :url    , 可选, 允许带参数, 在 QueryString 中附加 openid=:openid 重定向. <b>请勿用作用户身份验证.</b>

    <strong>响应</strong>
    <i>当终端是企业成员时</i>
        <i>当未附带 <code>redir</code> 参数时:</i>
        application/json
        UserId      :string , 当前用户的UserId=${UserId}.
        DeviceId    :string , (No Usage).

        <i>当附带 <code>redir</code> 参数时:</i>
        302 Found
        Location: :${redir}?UserId=${UserId}&DeviceId=${DeviceId}

    <i>当终端不是企业成员时</i>
        <i>当未附带 <code>redir</code> 参数时:</i>
        application/json
        OpenId      :string , 当前用户的OpenId=${OpenId}.
        DeviceId    :string , (No Usage).

        <i>当附带 <code>redir</code> 参数时:</i>
        302 Found
        Location: :${redir}?OpenId=${OpenId}&DeviceId=${DeviceId}

 * </pre>
 */
public class CorpSnsapiBase extends HttpServlet
{
    public static final String KEY_CORPID        = "corpid";
    public static final String KEY_CORPSECRET    = "corpsecret";

    protected static final String CODE  = "code";
    protected static final String REDIR = "redir";

    protected static final String USER_ID   = "UserId";
    protected static final String OPEN_ID   = "OpenId";
    protected static final String DEVICE_ID = "DeviceId";

    /** @deprecated SnsapiBase no longer use this field.
     */
    @Deprecated
    protected String appid;
    /** @deprecated SnsapiBase no longer use this field.
     */
    @Deprecated
    protected String secret;

    /** Servlet.init().
     * Default implement to initialize WxmpFactorySingl to ensure upstream
     * TokenProvider standby.
     * If you are building a multi-account env, you SHOULD override it.
     * If you are not preparing a wxpay.properties, you MUST override it.
     */
    public void init()
    {
        this.initSingl();

        return;
    }

    public void initSingl()
    {
        WxcpFactorySingl.getInstance();

        return;
    }

    /** 提供 appid 参数.
     * Servlet 从此方法取得必需参数 appid, 覆盖此方法可以自定义 appid 的来源.
     * 默认实现从配置文件 <code>/wxcp.properties</code> 取得
     * @deprecated obsoleted, use getCorpid() instead.
     */
    @Deprecated
    public final String getAppid(HttpServletRequest req)
        throws Exception
    {
        throw(new UnsupportedOperationException("getAppid() is obsoleted, migrated into getCorpid()."));
    }

    /** 提供 secret 参数.
     * Servlet 从此方法取得必需参数 secret, 覆盖此方法可以自定义 secret 的来源.
     * 默认实现从配置文件 <code>/wxcp.properties</code> 取得
     * @deprecated obsoleted, CorpSnsapiBase now directly invoking <code>getAssessToken(corpid)</code> instead.
     */
    public String getSecret(String appid)
        throws Exception
    {
        throw(new UnsupportedOperationException("getSecret() is obsoleted, migrated into getCorpsecret()."));
    }

    /** 提供 corpid 参数.
     * Servlet 从此方法取得必需参数 corpid, 或在缺省时从 WxcpFactorySingl 取得,
     * 覆盖此方法可以自定义 corpid 的来源.
     */
    public String getCorpid(HttpServletRequest req)
        throws Exception
    {
        String corpid = req.getParameter(KEY_CORPID);
        if (corpid != null)
            return(corpid);

        // else
        return(
            WxcpFactorySingl.getInstance().getCorpid()
        );
    }

    /** 提供 access_token 参数.
     * Servlet 从此方法取得必需参数 access_token, 覆盖此方法可以自定义 access_token 的来源.
     * 默认实现从 <code>WxcpTokenKeeper.getInstance(appid, secret).getAccessToken()</code> 取得
     * 默认实现的 <code>doGet()</code> 从 <code>this.getAppid()</code> 取得 appid,
     * 从 <code>this.getSecret()</code> 取得 secret.
     * @deprecated obsoleted, CorpSnsapiBase now directly invoking <code>getAssessToken(corpid)</code> instead.
     */
    @Deprecated
    public final String getAccessToken(String appid, String secret)
        throws Exception
    {
        throw(new UnsupportedOperationException("getAccessToken(appid, secret) is obsoleted, migrated into getAccessToken(corpid)."));
    }

    /** 提供 access_token 参数.
     * Servlet 从此方法取得必需参数 access_token, 覆盖此方法可以自定义 access_token 的来源.
     * 默认实现从 <code>this.getTokenProvider(corpid).getAccessToken()</code> 取得.
     */
    public String getAccessToken(String corpid)
    {
        return(
            this.getTokenProvider(corpid).getAccessToken()
        );
    }

    /** 提供 TokenProvider.
     * Servlet 从此方法取得 TokenProvider, 继而取得 access_token.
     * 默认实现从 <code>ATMagCp.getDefaultInstance().get(corpid)</code> 取得.
     * 覆盖此方法可以自定义 TokenProviderCp 的来源.
     * 或覆盖 <code>getAccessToken(corpid)</code> 以不使用 TokenProviderCp 作为 access_token 的来源.
     */
    public TokenProviderCp getTokenProvider(String corpid)
    {
        return(
            ATMagCp.getDefaultInstance().get(corpid)
        );
    }

    /** 在取得企业成员 UserId 后, 发送响应前的回调.
     * 覆盖此方法可以实现自己的逻辑.
     * 默认实现是 NOOP.
     */
    public void triggerCorpMember(GetuserinfoResponse resp, HttpServletRequest req)
        throws Exception
    {
        // NOOP

        return;
    }

    /** 在取得非企业成员 OpenId 后, 发送响应前的回调.
     * 覆盖此方法可以实现自己的逻辑.
     * 默认实现是 NOOP.
     */
    public void triggerNonCorpMember(GetuserinfoResponse resp, HttpServletRequest req)
        throws Exception
    {
        // NOOP

        return;
    }

    /** 在取得企业成员 UserId 后, 构造响应.
     * 覆盖此方法可以自行构造响应.
     * 默认实现如文档所述.
     */
    public void responseCorpMember(GetuserinfoResponse getuserinfoResponse, HttpServletRequest req, HttpServletResponse resp)
        throws Exception
    {
        JSONObject json = getuserinfoResponse.getJson();

        String userId   = json.getString(USER_ID);
        String deviceId = json.getString(DEVICE_ID);
        String redir = req.getParameter(REDIR);

        if (redir == null)
        {
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print(json.toString());
        }
        else
        {
            String rebuild = URLParser.fromURL(redir)
                .setParameter(USER_ID, userId)
                .setParameter(DEVICE_ID, deviceId)
                .toURL();
            resp.sendRedirect(rebuild);
        }

        return;
    }

    /** 在取得非企业成员 OpenId 后, 构造响应.
     * 覆盖此方法可以自行构造响应.
     * 默认实现如文档所述.
     */
    public void responseNonCorpMember(GetuserinfoResponse getuserinfoResponse, HttpServletRequest req, HttpServletResponse resp)
        throws Exception
    {
        JSONObject json = getuserinfoResponse.getJson();

        String openId   = json.getString(OPEN_ID);
        String deviceId = json.getString(DEVICE_ID);
        String redir    = req.getParameter(REDIR);

        if (redir == null)
        {
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print(json.toString());
        }
        else
        {
            String rebuild = URLParser.fromURL(redir)
                .setParameter(OPEN_ID, openId)
                .setParameter(DEVICE_ID, deviceId)
                .toURL();
            resp.sendRedirect(rebuild);
        }

        return;
    }

    public void onError(Exception ex, HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        this.getServletContext().log("Wxmp:SnsapiBase:FAIL:", ex);
        resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        try
        {
            req.setCharacterEncoding("utf-8");

            String code = req.getParameter(CODE);

            if (code == null)
            {
                String thisUrl = URLBuilder.encodeURIComponent(
                    req.getRequestURL()
                        .append(req.getQueryString()!=null?"?"+req.getQueryString():"")
                        .toString()
                );

                String url = new Oauth2Authorize(this.getAppid(req), thisUrl)
                    .build()
                    .toURL();

                resp.sendRedirect(url);

                return;
            }

            // else
            String corpid = this.getCorpid(req);
            String accessToken = this.getAccessToken(corpid);
            GetuserinfoResponse getuserinfoResp = new Getuserinfo(accessToken, code)
                .execute();

            // DEBUG
            System.out.println(getuserinfoResp.getJson().toString());

            if (getuserinfoResp.isErrorEncountered())
                throw(new WxcpException(getuserinfoResp.getErrcode(), getuserinfoResp.getErrmsg()));

            // else
            if (getuserinfoResp.isCorpMember())
            {
                this.triggerCorpMember(getuserinfoResp, req);
                this.responseCorpMember(getuserinfoResp, req, resp);
            }
            else
            {
                this.triggerNonCorpMember(getuserinfoResp, req);
                this.responseNonCorpMember(getuserinfoResp, req, resp);
            }
        }
        catch (Exception ex)
        {
            this.onError(ex, req, resp);
        }

        return;
    }

}
