package com.github.cuter44.wxmp.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;

//import org.apache.http.client.*;
import org.apache.http.client.fluent.*;
import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.github.cuter44.wxmp.util.*;

/** 网页授权(snsapi_base)的基础实现, 为网页前端取得当前用户的 openid.
 *
 * 关于该 servlet 的工作流程请参见 <a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">网页授权获取用户基本信息↗</a>
 *
 * 需要在微信客户端上执行
 *
 * <b>直至微信6.0为止都无法使用 Ajax 调用此方法</b>, 该尿性是由微信客户端造成的.
 *
 * <pre style="font-size:12px">

    GET /snsapi-base.api
    取得 openid

    <strong>参数</strong>
    <i>code    :string , 从 open.weixin.qq.com 跳转时带入, 无需客户端处理.</i>
    redir   :url    , 可选, 允许带参数, 在 QueryString 中附加 openid=:openid 重定向. <b>请勿用作用户身份验证.</b>

    <strong>响应</strong>
    <i>当未附带 <code>redir</code> 参数时:</i>
    application/json
    openid  :string , 当前用户的openid=${openid}.

    <i>当附带 <code>redir</code> 参数时:</i>
    302 Found
    Location: :${redir}?openid=${openid}

 * </pre>
 */
public class SnsapiBase extends HttpServlet
{
    public static final String KEY_APPID     = "appid";
    public static final String KEY_SECRET    = "SECRET";

    protected static final String CODE      = "code";
    protected static final String REDIR     = "redir";

    protected static final String OPENID    = "openid";

    protected String appid;
    protected String secret;

    /** 提供 appid 参数
     * servlet 从此方法取得必需参数 appid, 覆盖此方法可以自定义 appid 的来源.
     * 默认实现从配置文件 /wxpay.properties 读取
     */
    public String getAppid(HttpServletRequest req)
    {
        return(this.appid);
    }

    /** 提供 secret 参数
     * servlet 从此方法取得必需参数 secret, 覆盖此方法可以自定义 secret 的来源.
     * 默认实现从配置文件 /wxpay.properties 读取
     */
    public String getSecret(String appid)
    {
        return(this.secret);
    }

    /** 在取得 openid 后, 发送响应前的回调.
     * 覆盖此方法可以触发想要的动作
     * 默认实现是 NOOP
     */
    public void trigger(SnsOAuthAccessTokenResponse resp, HttpServletRequest req)
    {
        // NOOP

        return;
    }

    /** 构造响应
     * 覆盖此方法可以自行构造响应
     * 默认实现如文档所述
     */
    public void response(SnsOAuthAccessTokenResponse snsapiBaseResp, HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        JSONObject json = snsapiBaseResp.getJson();

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
            resp.getWriter().print("{\"openid\":\""+openid+"\"}");

            return;
        }

        // else
        String rebuild = URLParser.fromURL(redir)
            .setParameter(OPENID, openid)
            .toURL();
        resp.sendRedirect(rebuild);

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
        System.err.println("ERROR: SnsapiBase failed.");
        ex.printStackTrace();
    }

    /** 读取配置文件
     * 覆盖此方法可以删除对配置文件的访问.
     */
    @Override
    public void init(ServletConfig config)
    {
        Properties conf = WxmpFactory.getDefaultInstance().getConf();
        this.appid = conf.getProperty(KEY_APPID);
        this.secret = conf.getProperty(KEY_SECRET);

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

                String url = new Oauth2Authorize.SnsapiBase(this.getAppid(req), thisUrl)
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

            this.trigger(snsapiBaseResp, req);

            this.response(snsapiBaseResp, req, resp);
        }
        catch (Exception ex)
        {
            this.onError(ex, req, resp);
        }

        return;
    }

}
