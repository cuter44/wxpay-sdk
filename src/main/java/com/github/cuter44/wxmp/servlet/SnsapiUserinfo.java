package com.github.cuter44.wxmp.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;
import java.net.URLEncoder;

//import org.apache.http.client.*;
import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.github.cuter44.wxmp.util.*;

/** 网页授权(snsapi_userinfo)的基础实现, 为网页前端取得当前用户的 openid 及其他信息.
 *
 * 关于该 servlet 的工作流程请参见 <a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">网页授权获取用户基本信息↗</a>
 *
 * 需要在微信客户端上执行
 *
 * <b>直至微信6.0为止都无法使用 Ajax 调用此方法</b>, 该尿性是由微信客户端造成的.
 *
 * <pre style="font-size:12px">

    GET /snsapi-userinfo.api
    取得 openid, 头像等

    <strong>参数</strong>
    <i>code    :string , 从 open.weixin.qq.com 跳转时带入, 无需客户端处理.</i>
    redir   :url    , 可选, 允许带参数, 在 QueryString 中附加 openid=:openid 重定向. <b>请勿用作用户身份验证.</b>

    <strong>响应</strong>
    <i>当未附带 <code>redir</code> 参数时:</i>
    application/json
    openid      :string     , 当前用户的openid
    nickname    :string     , 当前用户的名字
    headimgurl  :url-http   , 当前用户的头像的 URL
    etc.

    <i>当附带 <code>redir</code> 参数时:</i>
    302 Found
    Location: $redir?openid=$openid

 * </pre>
 */
public class SnsapiUserinfo extends HttpServlet
{
    public static final String KEY_APPID     = "appid";
    public static final String KEY_SECRET    = "SECRET";

    protected static final String CODE      = "code";
    protected static final String REDIR     = "redir";

    protected static final String OPENID    = "openid";

    /** @deprecated SnsapiUserinfo no longer use this field.
     */
    @Deprecated
    protected String appid;
    /** @deprecated SnsapiUserinfo no longer use this field.
     */
    @Deprecated
    protected String secret;

    /** Servlet.init();
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
        WxmpFactorySingl.getInstance();

        return;
    }


    /** 提供 appid 参数
     * Servlet 从此方法取得必需参数 appid, 或在缺省时从 WxmpFactorySingl 取得,
     * 覆盖此方法可以自定义缺省参数时 appid 的来源.
     */
    public String getAppid(HttpServletRequest req)
        throws Exception
    {
        String appid = req.getParameter(KEY_APPID);
        if (appid != null)
            return(appid);

        // else
        return(
            WxmpFactorySingl.getInstance().getTokenProvider().getAppid()
        );
    }

    /** 提供 secret 参数
     * servlet 从此方法取得必需参数 secret, 覆盖此方法可以自定义 secret 的来源.
     * 默认实现从 ATMag.getDefaultInstance().get(appid) 取得.
     */
    public String getSecret(String appid)
        throws Exception
    {
        return(
            ATMag.getDefaultInstance().get(appid).getSecret()
        );
    }

    /** 在取得 openid 后, 发送响应前的回调.
     * 覆盖此方法可以触发想要的动作
     * 默认实现是 NOOP
     */
    public void trigger(SnsUserinfoResponse resp, HttpServletRequest req)
        throws Exception
    {
        // NOOP

        return;
    }

    /** 构造响应
     * 覆盖此方法可以自行构造响应
     * 默认实现如文档所述
     */
    public void response(SnsUserinfoResponse snsUserinfoResp, HttpServletRequest req, HttpServletResponse resp)
        throws Exception
    {
        JSONObject json = snsUserinfoResp.getJson();

        String openid = json.getString(OPENID);
        if (openid == null)
        {
            resp.setStatus(500);
            resp.setContentType("application/json");
            resp.getWriter().print(json.toString());

            return;
        }

        // else
        String redir = req.getParameter(REDIR);
        if (redir == null)
        {
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print(json.toString());

            return;
        }

        // else
        String rebuild = URLParser.fromURL(redir)
            .setParameter(OPENID, openid)
            .toURL();
        resp.sendRedirect(rebuild);

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

                String url = new Oauth2Authorize.SnsapiUserinfo(this.getAppid(req), thisUrl)
                    .build()
                    .toURL();

                resp.sendRedirect(url);

                return;
            }

            // else
            String appid = this.getAppid(req);
            String secret = this.getSecret(appid);
            SnsOAuthAccessTokenResponse snsapiBaseResp = new SnsOAuthAccessToken(appid, secret, code)
                .execute();

            SnsUserinfoResponse snsUserinfoResp = new SnsUserinfo(snsapiBaseResp)
                .execute();

            this.trigger(snsUserinfoResp, req);

            this.response(snsUserinfoResp, req, resp);
        }
        catch (Exception ex)
        {
            this.onError(ex, req, resp);
        }

        return;
    }

}
