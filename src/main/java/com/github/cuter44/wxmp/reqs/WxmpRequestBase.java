package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import javax.net.ssl.SSLContext;

import com.github.cuter44.nyafx.crypto.*;
import com.github.cuter44.nyafx.text.*;
import org.apache.http.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
//import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.config.RequestConfig;
import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.WxmpException;
import com.github.cuter44.wxmp.resps.WxmpResponseBase;
import com.github.cuter44.wxmp.util.JSONMaterializer;

/**
 * @author galin<cuter44@foxmail.com>
 * @date 2014/12/25
 */
public abstract class WxmpRequestBase
{
    public static final String KEY_SKIP_STATUS_CODE_CHECK = "SKIP_STATUS_CODE_CHECK";
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
    public WxmpRequestBase configHC(SSLContext ctx)
    {
        this.httpClient = buildHttpClient(ctx);

        return(this);
    }


  // CONSTRUCT
    public WxmpRequestBase(Properties aConf)
    {
        this.conf = aConf;

        return;
    }

  // CONFIG
    protected Properties conf;

    public final String getProperty(String key)
    {
        return(
            this.conf.getProperty(key)
        );
    }

    /** Set property/parameter to request.
     * Chaining invoke supported.
     */
    public final WxmpRequestBase setProperty(String key, String value)
    {
        this.conf.setProperty(key, value);
        return(this);
    }

    /** Merge key-value into internal config map.
     *
     * @param aConf a Map contains key-value pairs, where key must be String, and values must implement toString() at least.
     */
    @SuppressWarnings("unchecked")
    public final WxmpRequestBase setProperties(Map aConf)
    {
        for (Object k:aConf.keySet())
            this.conf.put(k, aConf.get(k).toString());

        return(this);
    }

  // BUILD
   public abstract WxmpRequestBase build();

  // TO_URL
    /** Extract URL to execute request on client
     */
    public abstract String toURL()
        throws UnsupportedOperationException;

    /** Provide query string to sign().
     * toURL() may not invoke this method.
     */
    protected String toQueryString(List<String> paramNames)
    {
        URLBuilder ub = new URLBuilder();

        for (String key:paramNames)
            ub.appendParam(key, this.getProperty(key));

        return(ub.toString());
    }

    protected static JSONObject buildJSONBody(JSONObject schema, Properties p)
    {
        return(
            JSONMaterializer.instance.materialize(schema, p)
        );
    }

  // EXECUTE
    /** Execute the constructed query
     */
    public abstract WxmpResponseBase execute()
        throws IOException, WxmpException, UnsupportedOperationException;

    /**
     */
    public String executeGet(String fullURL)
        throws IOException
    {
        HttpGet req = new HttpGet(fullURL);

        CloseableHttpResponse resp = this.getHttpClient().execute(req);

        try
        {
            if (!Boolean.valueOf(this.getProperty(KEY_SKIP_STATUS_CODE_CHECK)))
                checkStatusCode(resp);
            return(getResponseBody(resp));
        }
        finally
        {
            resp.close();
        }
    }

    public String executePostJSON(String fullURL, String bodyJSON)
        throws IOException
    {
        HttpPost req = new HttpPost(fullURL);

        // DEBUGING set proxy if need to capture the traffic
        // remember to load your proxy certificate
        //RequestConfig rc = RequestConfig.custom()
            //.setProxy(
                //new HttpHost("10.50.9.21", 8888)
            //)
            //.build();
        //req.setConfig(rc);

        req.setEntity(
            new StringEntity(
                bodyJSON,
                ContentType.create("application/json", "utf-8")
            )
        );

        CloseableHttpResponse resp = this.getHttpClient().execute(req);

        try
        {
            if (!Boolean.valueOf(this.getProperty(KEY_SKIP_STATUS_CODE_CHECK)))
                checkStatusCode(resp);
            return(getResponseBody(resp));
        }
        finally
        {
            resp.close();
        }
    }

    public String executePostJSON(String fullURL, JSONObject bodyJSON)
        throws IOException
    {
        return(
            this.executePostJSON(fullURL, bodyJSON.toString())
        );
    }

    /** @return a http client, ought to be used to process the request.
     */
    protected CloseableHttpClient getHttpClient()
    {
        return(
            (this.httpClient != null) ? this.httpClient : defaultHttpClient
        );
    }

  // MISC
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

    protected static CloseableHttpResponse checkStatusCode(CloseableHttpResponse resp)
        throws IOException
    {
        StatusLine s = resp.getStatusLine();
        int sc = s.getStatusCode();

        switch (sc)
        {
            case 100:
            case 200:
            case 201:
            case 202:
            case 204:
                return(resp);
            default:
                throw(new IOException(
                    "Wxmp:WxmpRequestBase:Abnormal HTTP response:\n"
                    +resp.toString()
                    +"\n"
                    +getResponseBody(resp)
                ));
        }
    }

}

