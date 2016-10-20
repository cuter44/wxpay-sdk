package com.github.cuter44.wxpay.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.net.ssl.SSLContext;

import com.github.cuter44.nyafx.crypto.*;
import com.github.cuter44.nyafx.text.*;
import org.apache.http.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
//import org.apache.http.client.*;
import org.apache.http.client.methods.*;

import com.github.cuter44.wxpay.WxpayException;
import com.github.cuter44.wxpay.WxpayProtocolException;
import com.github.cuter44.wxpay.resps.WxpayResponseBase;

/**
 * @author galin<cuter44@foxmail.com>
 * @date 2014/12/25
 */
public abstract class WxpayRequestBase
{
    public static final String KEY_APPID         = "appid";
    public static final String KEY_SIGN          = "sign";
    public static final String KEY_KEY           = "KEY";
    public static final String KEY_SECRET        = "SECRET";
    public static final String KEY_NOTIFY_URL    = "notify_url";
    public static final String KEY_NONCE_STR     = "nonce_str";

    protected static CryptoBase crypto = CryptoBase.getInstance();

    /** Length of generated nonceStr, default to 8, in bytes
     */
    protected static final int NONCE_STR_BYTES = 8;

  // SSL
    /** Default http client to use to send request to weixin server.
     * Provide class-scope http client, which is used when <code>httpClient</code> is null, major for single-account use.
     * You can tweak this with your own. This will takes effect on follow-up request whose <code>httpClient</code> is unset.
     */
    public static CloseableHttpClient defaultHttpClient;

    /** Http client to use to send request to weixin server.
     * Provide object-scope http client, major for multi-account use.
     * You can directly set this field. This will takes effect on time when <code>.execute()</code> is called.
     */
    public CloseableHttpClient httpClient;

    protected static CloseableHttpClient buildHttpClient(SSLContext ctx)
    {
        HttpClientBuilder hcb = HttpClientBuilder.create()
            .disableAuthCaching()
            .disableCookieManagement()
            .setSslcontext(ctx);

        return(hcb.build());
    }

    /** Config defualt http client
     * The existing <code>defaultHttpClient</code> will be dropped, without closing.
     */
    public static void configDefaultHC(SSLContext ctx)
    {
        defaultHttpClient = buildHttpClient(ctx);
    }

    /** Config http client
     * The existing <code>httpClient</code> will be dropped, without closing.
     * @return this
     */
    public WxpayRequestBase configHC(SSLContext ctx)
    {
        this.httpClient = buildHttpClient(ctx);

        return(this);
    }

  // CONSTRUCT
    public WxpayRequestBase(Properties aConf)
    {
        this.conf = aConf;
        this.setNonceStr(
            crypto.byteToHex(crypto.randomBytes(NONCE_STR_BYTES))
        );

        return;
    }

  // CONFIG
    protected Properties conf;

    public String getProperty(String key)
    {
        return(
            this.conf.getProperty(key)
        );
    }

    /**
     * chain supported
     */
    public WxpayRequestBase setProperty(String key, String value)
    {
        this.conf.setProperty(key, value);
        return(this);
    }

    //public WxpayRequestBase setCDATAProperty(String key, String value)
    //{
        //this.conf.setProperty(key, "<![CDATA["+value+"]]>");
        //return(this);
    //}

    /**
     * batch setProperty
     * @param aConf a Map contains key-value pairs, where key must be String, and values must implement toString() at least.
     */
    public WxpayRequestBase setProperties(Map aConf)
    {
        this.conf.putAll(aConf);
        return(this);
    }

  // BUILD
   public abstract WxpayRequestBase build();

  // SIGN
    /** sign
     * SUB-CLASS MUST IMPLEMENT THIS METHOD TO BE CALLBACKED.
     * @exception UnsupportedOperationException if <code>sign_type</code> is other than <code>MD5</code>
     * @exception IllegalStateException if <code>Key</code> or something else (related to algorithm) not found
     */
    public abstract WxpayRequestBase sign()
        throws UnsupportedEncodingException, UnsupportedOperationException, IllegalStateException;

    /**
     * @param paramNames key names to submit, in dictionary order
     */
    protected String sign(List<String> paramNames)
        throws UnsupportedEncodingException, UnsupportedOperationException, IllegalStateException
    {
        String key = this.getProperty(KEY_KEY);

        String sign = this.signMD5(paramNames, key);

        //this.setCDATAProperty(KEY_SIGN, sign);
        this.setProperty(KEY_SIGN, sign);

        return(sign);
    }

    /**
     * caculate sign according to wxp-spec
     * @exception UnsupportedEncodingException if your runtime does not support utf-8.
     */
    protected String signMD5(List<String> paramNames, String key)
        throws UnsupportedEncodingException
    {
        if (key == null)
            throw(new IllegalArgumentException("KEY required to sign, but not found."));

        StringBuilder sb = new StringBuilder()
            .append(this.toQueryString(paramNames))
            .append("&key="+key);

        String sign = this.crypto.byteToHex(
            this.crypto.MD5Digest(
                sb.toString().getBytes("utf-8")
        ));

        sign = sign.toUpperCase();

        return(sign);
    }

    /** Provide query string to sign().
     * This method do not parse sign, thus toURL() must not invoke this method.
     */
    protected String toQueryString(List<String> paramNames)
    {
        URLBuilder ub = new URLBuilder();

        for (String key:paramNames)
        {
            if (KEY_SIGN.equals(key))
                continue;

            String value = this.getProperty(key);
            if (value == null || value.isEmpty())
                continue;

            ub.appendParam(key, value);
        }

        return(ub.toString());
    }

  // TO_URL
    /** Extract URL to execute request on client
     */
    public abstract String toURL()
        throws UnsupportedOperationException;

  // TO_XML
    protected String buildXMLBody(List<String> paramNames)
    {
        StringBuilder xml = new StringBuilder();

        xml.append("<xml>");

        for (String k:paramNames)
        {
            String v = this.getProperty(k);
            if (v != null)
                xml.append('<').append(k).append('>')
                   .append(v)
                   .append("</").append(k).append('>');
        }

        //xml.append('<').append(KEY_SIGN).append('>')
           //.append(this.getProperty(KEY_SIGN))
           //.append("</").append(KEY_SIGN).append('>');

        xml.append("</xml>");

        return(xml.toString());
    }


  // EXECUTE
    /** Execute the constructed query
     */
    public abstract WxpayResponseBase execute()
        throws WxpayException, WxpayProtocolException, IOException;

    /** @deprecated since 0.4.5 as no longer used.
     */
    protected static String getResponseBody(HttpResponse resp)
        throws IOException
    {
        HttpEntity he = resp.getEntity();

        Long l = he.getContentLength();
        ByteArrayOutputStream buffer = (l > 0) ? new ByteArrayOutputStream(l.intValue()) : new ByteArrayOutputStream();
        resp.getEntity().writeTo(buffer);

        String content = buffer.toString("utf-8");

        return(content);
    }

    /** @return a http client, ought to be used to process the request.
     */
    protected CloseableHttpClient getHttpClient()
    {
        return(
            (this.httpClient != null) ? this.httpClient : defaultHttpClient
        );
    }

    /**
     * @return response body as InputStream, MUST be closed by invoker.
     */
    public InputStream executePostXML(String fullURL, String bodyXML)
        throws IOException
    {
        CloseableHttpClient hc = (this.httpClient != null) ? this.httpClient : defaultHttpClient;

        HttpPost req = new HttpPost(fullURL);
        req.setEntity(
            new StringEntity(
                bodyXML,
                ContentType.create("text/xml", "utf-8")
            )
        );

        CloseableHttpResponse resp = hc.execute(req);

        return(
            resp.getEntity().getContent()
        );
    }

  // MISC
    public WxpayRequestBase setAppid(String appid)
    {
        this.setProperty(KEY_APPID, appid);

        return(this);
    }

    public WxpayRequestBase setNonceStr(String nonceStr)
    {
        this.setProperty(KEY_NONCE_STR, nonceStr);

        return(this);
    }
}

