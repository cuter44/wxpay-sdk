package com.github.cuter44.wxcp.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;

//import org.apache.http.client.*;
import org.apache.http.client.fluent.*;
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

    protected String appid;
    protected String secret;

    /** 提供 appid 参数
     * servlet 从此方法取得必需参数 appid, 覆盖此方法可以自定义 appid 的来源.
     * 默认实现从配置文件 <code>/wxcp.properties</code> 取得
     */
    public String getAppid(HttpServletRequest req)
    {
        return(this.appid);
    }

    /** 提供 secret 参数
     * servlet 从此方法取得必需参数 secret, 覆盖此方法可以自定义 secret 的来源.
     * 默认实现从配置文件 <code>/wxcp.properties</code> 取得
     */
    public String getSecret(String appid)
    {
        return(this.secret);
    }

    /** 提供 access_token 参数
     * servlet 从此方法取得必需参数 access_token, 覆盖此方法可以自定义 access_token 的来源.
     * 默认实现从 <code>WxcpTokenKeeper.getInstance(appid, secret).getAccessToken()</code> 取得
     * 默认实现的 <code>doGet()</code> 从 <code>this.getAppid()</code> 取得 appid,
     * 从 <code>this.getSecret()</code> 取得 secret.
     */
    public String getAccessToken(String appid, String secret)
    {
        return(
            WxcpTokenKeeper.getInstance(appid, secret).getAccessToken()
        );
    }

    /** 在取得企业成员 UserId 后, 发送响应前的回调.
     * 覆盖此方法可以实现自己的逻辑
     * 默认实现是 NOOP
     */
    public void triggerCorpMember(GetuserinfoResponse resp, HttpServletRequest req)
    {
        // NOOP

        return;
    }

    /** 在取得非企业成员 OpenId 后, 发送响应前的回调.
     * 覆盖此方法可以实现自己的逻辑
     * 默认实现是 NOOP
     */
    public void triggerNonCorpMember(GetuserinfoResponse resp, HttpServletRequest req)
    {
        // NOOP

        return;
    }

    /** 在取得企业成员 UserId 后, 构造响应
     * 覆盖此方法可以自行构造响应
     * 默认实现如文档所述
     */
    public void responseCorpMember(GetuserinfoResponse getuserinfoResponse, HttpServletRequest req, HttpServletResponse resp)
        throws IOException
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

    /** 在取得非企业成员 OpenId 后, 构造响应
     * 覆盖此方法可以自行构造响应
     * 默认实现如文档所述
     */
    public void responseNonCorpMember(GetuserinfoResponse getuserinfoResponse, HttpServletRequest req, HttpServletResponse resp)
        throws IOException
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

    /** Trigger on error encountered
     * <br />
     * This method can be overrided to plant your own error handling implemention.
     * <br />
     * Default behavior is <code>ex.printStackTrace()</code>
     */
    public void onError(Exception ex, HttpServletRequest req, HttpServletResponse resp)
        throws ServletException
    {
        System.err.println("ERROR: CorpSnsapiBase failed.");
        ex.printStackTrace();
    }

    /** 读取配置文件
     * 覆盖此方法可以删除对配置文件的访问.
     */
    @Override
    public void init(ServletConfig config)
    {
        Properties conf = WxcpFactory.getDefaultInstance().getConf();
        this.appid = conf.getProperty(KEY_CORPID);
        this.secret = conf.getProperty(KEY_CORPSECRET);

        return;
    }

    /** Block ambiguous <code>init()</code> inherition.
     */
    @Override
    public final void init()
    {
        return;
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
                String thisUrl = req.getRequestURL()
                    .append(req.getQueryString()!=null?"?"+req.getQueryString():"")
                    .toString();

                String url = new Oauth2Authorize(this.getAppid(req), thisUrl)
                    .build()
                    .toURL();

                resp.sendRedirect(url);

                return;
            }

            // else
            String appid = this.getAppid(req);
            String accessToken = this.getAccessToken(appid, this.getSecret(appid));
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
