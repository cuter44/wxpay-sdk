package com.github.cuter44.wxmp;

import java.io.InputStreamReader;
import java.util.Properties;
import java.util.MissingResourceException;
import javax.net.ssl.SSLContext;

import org.apache.http.*;
import org.apache.http.impl.client.*;

//import com.github.cuter44.wxpay.reqs.*;
import com.github.cuter44.wxmp.util.CertificateLoader;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.util.*;

/** WxmpFactroy base implemention and code snippet lib.
 * <br />
 * Define most convention that should be demanded. It is refactored from the
 * legacy WxmpFactory. This one is aimed to provide more flexibility, by
 * tearing codes into separated methods. And clearly defines the lifecycle phrases.
 * <p />
 * It is not encouraged to instantiate this class in singlar mode, instead
 * please use WxmpFactorySingl.
 * <p />
 * If you are a skilled geek, and do want something other than provided, you
 * must want to skim the source. It provides clues.
 */
public class WxmpFactoryBase
{
  // CONSTANT
    protected static final String KEY_ACCESS_TOKEN = "access_token";

  // CONTRUCT
    /** Noop constructor for next hierarchy
     */
    public WxmpFactoryBase()
    {
        return;
    }

  // SELECTIVE INITIALIZE COMPONENT
    // Customize your constructor with following pre-made encapsulation,
    // or even a self-implemented one.
    // Note that they should be run sequentially, or you may encountered from
    // errors. If you don't know how or why, see WxmpFactorySingl.

  // CONFIG
    /** Configuration and preset readed from wxpay.properties.
     * It is allowed to programaticlally modify, or even allow tweak before bootstraping.
     */
    protected Properties conf;

    public Properties getConf()
    {
        return(this.conf);
    }

    protected void configFromFile(String resource)
        throws MissingResourceException
    {
        try
        {
            Properties p = new Properties();
            p.load(
                new InputStreamReader(
                    this.getClass().getResourceAsStream(resource),
                    "utf-8"
            ));

            this.conf = p;
        }
        catch (Exception ex)
        {
            MissingResourceException mrex = new MissingResourceException(
                "Failed to load conf resource.",
                this.getClass().getName(),
                resource
            );
            mrex.initCause(ex);

            throw(mrex);
        }
    }

  // HTTP CLIENT
    protected CloseableHttpClient httpClient;

    public CloseableHttpClient getHttpClient()
    {
        return(this.httpClient);
    }

    /** Prepare a HttpClient and directly assign to <code>WxmpRequestBase.defaultHttpClient</code>.
     * This action will tweak the previous one(if set). Hence playing more than once
     * may lead to memory leak.
     */
    protected void setupHttpClientGlobal()
    {
        SSLContext ssl =
            new CertificateLoader()
                .config(this.conf)
                .asSSLContext();

        WxmpRequestBase.defaultHttpClient =
            HttpClientBuilder.create()
                .disableAuthCaching()
                .disableCookieManagement()
                .setSslcontext(ssl)
                .build();

        return;
    }

    /** Prepare a HttpClient and assign to <code>this.httpClient</code>.
     * In practice this is redundant, since wxmp APIs does not need a client-side
     * certificate, either even a separated SSL context.
     * So this method is just made to play a joke.(not
     */
    protected void setupHttpClient()
    {
        SSLContext ssl =
            new CertificateLoader()
                .config(this.conf)
                .asSSLContext();

        this.httpClient =
            HttpClientBuilder.create()
                .disableAuthCaching()
                .disableCookieManagement()
                .setSslcontext(ssl)
                .build();

        return;
    }

  // ACCESS TOKEN
    /** TokenProvider which provides management of <code>access_token</code>.
     */
    protected TokenProvider tokenProvider;

    public TokenProvider getTokenProvider()
    {
        return(this.tokenProvider);
    }

    /** Initialize TokenProvider according to <code>this.conf</code>.
     * Acquire via <code>ATMag.getDefaultInstance().getOrCreate(this.conf)</code>,
     * which guarantee they are not recreated or interfered mutually.
     * @throws RuntimeException (to wrap a checked exception) on initialization failed, but this should rarely occure.
     */
    protected void initTokenProvider()
    {
        try
        {
            this.tokenProvider = ATMag.getDefaultInstance().getOrCreate(this.conf);
        }
        catch (Exception ex)
        {
            throw(new RuntimeException(ex));
        }
    }

  // FACTORY
    /** Instantiate and inject <code>this.conf</code> into the request.
     * Requires the target req class implement
     * @param clazz Subclass of WxmpRequestBase, must implement constructor `clazz(Properties)`.
     */
    public <X extends WxmpRequestBase> X instantiate(Class<X> clazz)
        throws Exception
    {
        return(
            clazz.getConstructor(Properties.class).newInstance(new Properties(this.conf))
        );
    }

    /** Inject access_token into given req, and return it.
     */
    public <X extends WxmpRequestBase> X setToken(X req)
    {
        req.setProperty(KEY_ACCESS_TOKEN, this.getTokenProvider().getAccessToken());

        return(req);
    }

    /** Inject httpClient into given req, and return it.
     */
    public <X extends WxmpRequestBase> X setHC(X req)
    {
        req.httpClient = this.httpClient;

        return(req);
    }
}
