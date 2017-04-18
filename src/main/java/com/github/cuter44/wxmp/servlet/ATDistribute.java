package com.github.cuter44.wxmp.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;

import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.text.*;
import com.github.cuter44.nyafx.crypto.*;
import static com.github.cuter44.nyafx.servlet.ParamsX.*;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.github.cuter44.wxmp.util.*;

/** Access token distributing portal.
 *
 * 从 ATMag 选取 access_token 并回复. 用于 access_token 再分发.
 *
 * <pre style="font-size:12px">

    GET /token.api

    <strong>参数</strong>
    appid           :string             , 可选, 取得指定的 appid 对应的 access_token
                                          缺省使用单例模式 WxmpFactorySingl 对应的 appid.

    <strong>响应</strong>
    application/json; charset=utf-8
    access_token    :string             , 获取到的凭证
    expires_in      :int/period-sec     , 凭证有效时间，单位：秒
    expires         :long/timestamp-ms  , 凭证终止时间

 * </pre>
 */
public class ATDistribute extends HttpServlet
{
    public static final String KEY_APPID        = "appid";
    public static final String ACCESS_TOKEN     = "access_token";
    public static final String EXPIRES_IN       = "expires_in";
    public static final String EXPIRES          = "expires";

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
            WxmpFactorySingl.getInstance().getAppid()
        );
    }

    /** Get token via appid
     * Servlet 从此方法取得 TokenProvider, 默认实现从 <code>ATMag.getDefaultInstance().get(appid)</code> 取得.
     * 覆盖此方法以更改其行为.
     */
    public TokenProvider getTokenProvider(String appid)
        throws Exception
    {
        return(
            ATMag.getDefaultInstance().get(appid)
        );
    }

    /** 校验请求
     * Servlet 调用此方法以检定是否为允许该请求, 覆盖此方法可以自行配置策略.
     * 默认实现仅允许 127.0.0.0/8(IPv4 only) 或 远端地址等于本地地址.
     * 如果要阻止操作, 抛出任意异常.
     * @see com.github.cuter44.wxpay.TokenKeeper
     */
    public void checkAccept(HttpServletRequest req)
        throws Exception
    {
        String c = req.getRemoteAddr();
        if (c.startsWith("127."))
            return;

        // else
        String s = req.getLocalAddr();
        if (s.equals(c))
            return;

        // else
        throw(new SecurityException("Wxmp:ATDistribute:Request rejected:"+c));
    }

    /** 构造响应
     * 覆盖此方法可以自行构造响应.
     * 默认实现如文档所述, 该响应兼容于微信的 convention.
     */
    public void response(HttpServletResponse resp, TokenProvider t)
        throws Exception
    {
        JSONObject json = new JSONObject();

        String a = t.getAccessToken();
        long x = t.getATExpire();

        json.put(ACCESS_TOKEN   , a                                     );
        json.put(EXPIRES        , x                                     );
        json.put(EXPIRES_IN     , (x - System.currentTimeMillis())/1000L);

        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write(json.toJSONString());

        return;
    }

    public void onError(Exception ex, HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        this.getServletContext().log("Wxmp:ATDistribute:FAIL:", ex);
        resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        req.setCharacterEncoding("utf-8");

        try
        {
            this.checkAccept(req);

            String appid = this.getAppid(req);

            TokenProvider t = this.getTokenProvider(appid);

            this.response(resp, t);
        }
        catch(Exception ex)
        {
            this.onError(ex, req, resp);
        }
    }

}
