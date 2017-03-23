package com.github.cuter44.wxmp.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;

//import org.apache.http.client.*;
import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.github.cuter44.wxmp.util.*;

/** 网页授权(snsapi_base)的基础实现, 为网页前端取得当前用户的 code, 交由第三方换取 openid.
 *
 * 关于该 servlet 的工作流程请参见 <a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">网页授权获取用户基本信息↗</a>
 *
 * 需要在微信客户端上执行
 *
 * <b>直至微信6.0为止都无法使用 Ajax 调用此方法</b>, 该尿性是由微信客户端造成的.
 *
 * <pre style="font-size:12px">

    GET /semi-snsapi-base.api
    取得 code

    <strong>参数</strong>
    redir   :url    , 可选, 允许带参数, 在 QueryString 中附加 code=:code 重定向. <b>请勿用作用户身份验证.</b>

    <strong>响应</strong>
    <i>当未附带 <code>redir</code> 参数时:</i>
    application/json
    code  :string , code.

    <i>当附带 <code>redir</code> 参数时:</i>
    302 Found
    Location: :${redir}?code=${code}

 * </pre>
 */
public class SemiSnsapiBase extends HttpServlet
{
    public static final String KEY_APPID     = "appid";
    public static final String KEY_SECRET    = "SECRET";

    protected static final String CODE      = "code";
    protected static final String REDIR     = "redir";

    protected String appid;

    /** @deprecated SemiSnsapiBase no longer use this field.
     */
    @Deprecated
    protected String secret;

    /** Servlet.init();
     * Default implement to initialize WxmpFactorySingl to ensure upstream
     * TokenProvider standby.
     * If you are building a multi-account env, you SHOULD override it.
     * If you are not preparing a wxpay.properties, you MUST override it.
     */
    @Override
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

    /** 提供 appid 参数.
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

    /** 处理 code.
     * 覆盖此方法可以自行构造响应
     * 默认实现如文档所述
     */
    public void code(String code, HttpServletRequest req, HttpServletResponse resp)
        throws Exception
    {
        String redir = req.getParameter(REDIR);
        if (redir == null)
        {
            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print("{\"code\":\""+code+"\"}");

            return;
        }

        // else
        String rebuild = URLParser.fromURL(redir)
            .setParameter(CODE, code)
            .toURL();
        resp.sendRedirect(rebuild);

        return;
    }

    public void onError(Exception ex, HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        this.getServletContext().log("Wxmp:SemiSnsapiBase:FAIL:", ex);
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

                String url = new Oauth2Authorize.SnsapiBase(this.getAppid(req), thisUrl)
                    .build()
                    .toURL();

                resp.sendRedirect(url);

                return;
            }

            // else
            this.code(code, req, resp);
        }
        catch (Exception ex)
        {
            this.onError(ex, req, resp);
        }

        return;
    }

}
