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

    protected String appid;
    protected String secret;
    protected TokenKeeper tokenKeeper;
    protected CryptoBase crypto = CryptoBase.getInstance();

    /** 读取配置文件
     * 覆盖此方法可以删除对配置文件的访问. 除非显式调用 super.init(config) 否则 getAppid(), getJSSDKTicket() 均需要自行实现.
     */
    @Override
    public void init(ServletConfig config)
    {
        WxmpFactory factory = WxmpFactory.getDefaultInstance();
        this.tokenKeeper = factory.getTokenKeeper();

        Properties conf = factory.getConf();
        this.appid = conf.getProperty(KEY_APPID);
        this.secret = conf.getProperty(KEY_SECRET);

        return;
    }

    /** 提供 appid 参数
     * servlet 从此方法取得必需参数 appid, 覆盖此方法可以自定义 appid 的来源.
     * 默认实现从配置文件 /wxpay.properties 读取
     */
    public String getAppid(HttpServletRequest req)
    {
        return(this.appid);
    }

    /** 提供 ticket 参数
     * servlet 从此方法取得必需参数 ticket, 覆盖此方法可以自定义 ticket 的来源.
     * 默认实现从 WxFactory.getDefaultInstance() 获取 TokenKeeper
     * @see com.github.cuter44.wxpay.TokenKeeper
     */
    public String getTicket(String appid)
    {
        return(
            this.tokenKeeper.getJSSDKTicket()
        );
    }

    /** 校验 URL
     * servlet 调用此方法以检定是否为传入的 url 参数生成签名, 覆盖此方法可以自行实现 url 鉴定策略以阻挡外部请求.
     * 默认实现直接返回. 如果要阻止操作, 抛出任意异常.
     * @see com.github.cuter44.wxpay.TokenKeeper
     */
    public void ifAcceptURL(String url)
    {
        return;
    }

    /** 构造响应
     * 覆盖此方法可以自行构造响应
     * 默认实现如文档所述
     */
    public void response(String appId, Long timestamp, String nonceStr, String signature, HttpServletResponse resp)
        throws IOException
    {
        JSONObject json = new JSONObject();

        json.put(APP_ID     , appId     );
        json.put(TIMESTAMP  , timestamp );
        json.put(NONCE_STR  , nonceStr  );
        json.put(SIGNATURE  , signature );

        resp.setContentType("application/json; utf-8");
        resp.getWriter().write(json.toJSONString());

        return;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        req.setCharacterEncoding("utf-8");

        String  appId       = getString(req, APP_ID);
                appId       = appId!=null?appId:this.getAppid(req);

        Long    timestamp   = getLong(req, TIMESTAMP);
                timestamp   = timestamp!=null?timestamp:System.currentTimeMillis();

        String  nonceStr    = getString(req, NONCE_STR);
                nonceStr    = nonceStr!=null?nonceStr:this.crypto.bytesToHex(this.crypto.randomBytes(8));

        String url = needString(req, URL);

        this.ifAcceptURL(url);

        String ticket = this.getTicket(appId);

        URLBuilder ub = new URLBuilder()
            .appendParam("jsapi_ticket", ticket)
            .appendParam("noncestr", nonceStr)
            .appendParam("timestamp", timestamp.toString())
            .appendParam("url", url);

        String signature = this.crypto.bytesToHex(
            this.crypto.SHA1Digest(
                ub.toString().getBytes("utf-8")
            )
        );

        this.response(appId, timestamp, nonceStr, signature, resp);
    }

}
