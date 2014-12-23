package com.github.cuter44.wxpay.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;

//import org.apache.http.client.*;
import org.apache.http.client.fluent.*;
import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxpay.*;

/**
 * This servlet parses params from <code>/wxpay.properties</code>
 * and returns client's <code>openid</code> facing the <code>appid</code>
 * Here how it goes:
 * 1. directly GET this url(which defined by your route file <code>web.xml</code>),
 *    <del>either an ajax call or</del> redirect is ok, see instruction below.
 * 2. due to Weixin's spec, client will be redirect to open.weixin.qq.com/blabla to obtain a <code>code</code>
 * 3. we will translate the <code>code</code> into <code>openid</code> and output it
 *
 * Response content-type in step #3 is alternative choice:
 * If it is a redirect request, a <code>redir</code> param is required in step #1,
 * we will append the <code>openid</code> as param and send a 302 response.
 * <del>If it is a ajax request, nothing but directly GET this url is needed.
 * In such case, openid will be output as text/plain.</del>
 * Since open.weixin.qq.com does not contains a cross-domain header, You must
 *
 * A same client will reflect in different openids while it faces different WX service accounts.
 * So DO NOT use this servlet in a multi-instance environment,
 * due to this servlet use a static appid and secret read from the config file.
 * <pre>
    Retrieve current client's openid
    GET /get-openid.api

    <strong>Params</strong>
    code    :string , generated and passed by open.weixin.qq.com, need not manual handling.
    redir   :url    , if you are doing an redirect and wish the openid returned as param k-v.

    <strong>Response</strong>
    <i>if <code>redir</code> absent</i>
    Content-Type: text/plain; charset=utf-8
    <i>if <code>redir</code> provided</i>
    302 Found
    Location: ${redir[@openid='$openid']}

 * </pre>
 */
public class GetOpenId extends HttpServlet
{
    protected static final String KEY_APPID     = "appid";
    protected static final String KEY_SECRET    = "SECRET";
    protected static final String KEY_OPENID    = "openid";

    protected static final String CODE      = "code";
    protected static final String REDIR     = "redir";

    protected Properties conf;

    @Override
    public void init()
    {
        this.conf = WxpayFactory.getDefaultInstance().getConf();

        return;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        req.setCharacterEncoding("utf-8");

        String code = req.getParameter(CODE);

        if (code == null)
        {
            String thisUrl = req.getRequestURL()
                .append('?')
                .append(req.getQueryString())
                .toString();

            String url = new URLBuilder()
                .appendPath("https://open.weixin.qq.com/connect/oauth2/authorize")
                .appendParam("appid", this.conf.getProperty(KEY_APPID))
                .appendParamEncode("redirect_uri", thisUrl)
                .appendParam("response_type", "code")
                .appendParam("scope", "snsapi_base")
                .appendLabel("wechat_redirect")
                .toString();

            resp.sendRedirect(url);

            return;
        }

        // else
        String url = new URLBuilder()
            .appendPath("https://api.weixin.qq.com/sns/oauth2/access_token")
            .appendParam("appid", this.conf.getProperty(KEY_APPID))
            .appendParam("secret", this.conf.getProperty(KEY_SECRET))
            .appendParam(CODE, code)
            .appendParam("grant_type", "authorization_code")
            .toString();

        String strJson = Request.Get(url)
                .execute()
                .returnContent()
                .asString();

        JSONObject json = JSON.parseObject(strJson);

        String openid = json.getString(KEY_OPENID);
        if (openid == null)
        {
            resp.setStatus(500);
            resp.setContentType("application/json");
            resp.getWriter().print(json.toString());

            return;
        }

        String redir = req.getParameter(REDIR);

        if (redir == null)
        {
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().print(openid);

            return;
        }

        //else
        String rebuild = URLParser.fromURL(redir)
            .setParameter(KEY_OPENID, openid)
            .toURL();

        resp.sendRedirect(rebuild);

        return;
    }

}
