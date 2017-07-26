package com.github.cuter44.wxmp.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;

import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.text.*;
import com.github.cuter44.nyafx.crypto.*;
import static com.github.cuter44.nyafx.servlet.Params.*;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.github.cuter44.wxmp.util.*;

/** JS-SDK config 签名 API
 *
 * 为 JS-SDK config 提供签名支持, 工作模式参见 <a href="http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E6.AD.A5.E9.AA.A4.E4.B8.89.EF.BC.9A.E9.80.9A.E8.BF.87config.E6.8E.A5.E5.8F.A3.E6.B3.A8.E5.85.A5.E6.9D.83.E9.99.90.E9.AA.8C.E8.AF.81.E9.85.8D.E7.BD.AE">微信JSSDK说明文档↗</a>
 *
 * <pre style="font-size:12px">

    GET /jssdk-config.api

    <strong>参数</strong>
    appId       :string         , 可选, APPID   , 缺省时由服务器取得
    timestamp   :unix-time-ms   , 可选, 时间戳  , 缺省时使用服务器时间
    nonceStr    :string         , 可选, 盐      , 缺省由服务器生成
    url         :url            , 必需, 当前 location.href , 不包含#及其后面部分

    <strong>响应</strong>
    application/json; charset=utf-8
    appId       :string         , APPID
    timestamp   :unix-time-ms   , 时间戳
    nonceStr    :string         , 盐
    signature   :string         , 签名

 * </pre>
 */
public class JSSDKConfig extends HttpServlet
{
    public static final String KEY_APPID     = "appid";
    public static final String KEY_SECRET    = "SECRET";
    public static final String KEY_OPENID    = "openid";

    protected static final String DEBUG         = "debug";
    protected static final String APP_ID        = "appId";
    protected static final String TIMESTAMP     = "timestamp";
    protected static final String NONCESTR      = "noncestr";
    protected static final String NONCE_STR     = "nonceStr";
    protected static final String SIGNATURE     = "signature";
    protected static final String URL           = "url";
    protected static final String JS_API_LIST   = "jsApiList";

    /** @deprecated JSSDKConfig no longer use this field.
     */
    @Deprecated
    protected String appid;
    /** @deprecated JSSDKConfig no longer use this field.
     */
    @Deprecated
    protected String secret;
    /** @deprecated JSSDKConfig no longer use this field.
     */
    @Deprecated
    protected TokenKeeper tokenKeeper;
    protected CryptoBase crypto = CryptoBase.getInstance();

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
     * 覆盖此方法可以自定义 appid 的来源.
     */
    public String getAppid(HttpServletRequest req)
        throws Exception
    {
        String appid = req.getParameter(KEY_APPID);
        if (appid != null)
            return(appid);

        // else
        return(
            WxmpFactorySingl.getInstance().getAppid()
        );
    }

    /** @Deprecated From 0.12 on get ticket from token provider, override getTokenProvider() instead.
     */
    @Deprecated
    public final String getTicket(String appid)
        throws Exception
    {
        throw(new UnsupportedOperationException("getTicket() is obsoleted, migrated into getTokenProvider()."));
    }

    /** 提供 TokenProvider
     * servlet 从此方法取得 TokenProvider, 覆盖此方法可以自定义 TokenProvider 的来源.
     * 默认实现从 <code>ATMag.getDefaultInstance().get(appid)</code> 取得.
     * Invoked every single req.
     * @see com.github.cuter44.wxpay.TokenKeeper
     */
    public TokenProvider getTokenProvider(String appid)
        throws Exception
    {
        return(
            ATMag.getDefaultInstance().get(appid)
        );
    }

    /** @Deprecated From 0.12 on use checkAccess() instead.
     */
    @Deprecated
    public final void ifAcceptURL(String url)
        throws Exception
    {
        throw(new UnsupportedOperationException("ifAcceptURL() is obsoleted, migrated into checkAccess()."));
    }

    /** 检查 URL.
     * servlet 调用此方法以检定是否为传入的 url 参数生成签名, 覆盖此方法可以自行实现 url 鉴定策略以阻挡外部请求.
     * 默认实现直接返回. 如果要阻止操作, 抛出任意异常.
     * @see com.github.cuter44.wxpay.TokenKeeper
     */
    public void checkAccess(String url)
        throws Exception
    {
        return;
    }

    @Deprecated
    public final void response(String appId, Long timestamp, String nonceStr, String signature, HttpServletResponse resp)
        throws Exception
    {
        throw(new UnsupportedOperationException("response() is obsoleted, migrated into response()(different parameters)."));
    }

    /** 构造响应
     * 覆盖此方法可以自行构造响应
     * 默认实现如文档所述
     */
    public void response(HttpServletResponse resp, TokenProvider t, Long timestamp, String nonceStr, String signature)
        throws Exception
    {
        JSONObject json = new JSONObject();

        json.put(APP_ID     , t.getAppid()  );
        json.put(TIMESTAMP  , timestamp     );
        json.put(NONCE_STR  , nonceStr      );
        json.put(SIGNATURE  , signature     );

        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(json.toJSONString());

        return;
    }

    public void onError(Exception ex, HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        this.getServletContext().log("Wxmp:JSSDKConfig:FAIL:", ex);
        resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        req.setCharacterEncoding("utf-8");

        try
        {
            String  appId       = this.getAppid(req);
            String  tm          = req.getParameter(TIMESTAMP);
            Long    timestamp   = tm!=null ? Long.valueOf(tm) : System.currentTimeMillis();
            String  nonceStr    = req.getParameter(NONCE_STR);
                    nonceStr    = nonceStr!=null ? nonceStr : this.crypto.bytesToHex(this.crypto.randomBytes(8));
            String  url         = needString(req, URL);

            this.checkAccess(url);
            TokenProvider t = this.getTokenProvider(appid);
            String ticket = t.getJSSDKTicket();

            URLBuilder ub = new URLBuilder()
                .appendParam("jsapi_ticket" , ticket                )
                .appendParam("noncestr"     , nonceStr              )
                .appendParam("timestamp"    , timestamp.toString()  )
                .appendParam("url"          , url                   );

            String signature = this.crypto.bytesToHex(
                this.crypto.SHA1Digest(
                    ub.toString().getBytes("utf-8")
                )
            );

            this.response(resp, t, timestamp, nonceStr, signature);
        }
        catch(Exception ex)
        {
            this.onError(ex, req, resp);
        }
    }

}
