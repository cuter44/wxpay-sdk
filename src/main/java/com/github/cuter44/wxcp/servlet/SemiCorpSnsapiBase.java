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

    GET /semi-corp-snsapi-base.api
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
public class SemiCorpSnsapiBase extends HttpServlet
{
    public static final String KEY_CORPID        = "corpid";
    public static final String KEY_CORPSECRET    = "corpsecret";

    protected static final String CODE  = "code";
    protected static final String REDIR = "redir";

    /** @deprecated CorpSemiSnsapiBase no longer use this field.
     */
    @Deprecated
    protected String appid;
    /** @deprecated CorpSemiSnsapiBase no longer use this field.
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

    /** 提供 corpid 参数.
     * Servlet 从此方法取得必需参数 appid, 或在缺省时从 WxmpFactorySingl 取得,
     * 覆盖此方法可以自定义 appid 的来源.
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
        this.getServletContext().log("Wxcp:SemiSnsapiBase:FAIL:", ex);
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
            this.code(code, req, resp);
        }
        catch (Exception ex)
        {
            this.onError(ex, req, resp);
        }

        return;
    }

}
