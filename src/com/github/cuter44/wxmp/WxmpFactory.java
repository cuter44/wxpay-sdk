package com.github.cuter44.wxmp;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.MissingResourceException;

//import com.github.cuter44.wxpay.reqs.*;
import com.github.cuter44.wxmp.util.CertificateLoader;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.util.*;

/** 微信MP工厂
 * <br />
 * 这个工厂对象维护配置文件并根据配置文件派生几乎所有的请求.
 */
public class WxmpFactory
{
  // CONSTANT
    private static final String RESOURCE_WXPAY_PROPERTIES = "/wxpay.properties";

    protected static final String KEY_APPID = "appid";
    protected static final String KEY_SECRET = "SECRET";

    protected static final String KEY_ACCESS_TOKEN = "access_token";

  // CONFIG
    protected Properties conf;

    public Properties getConf()
    {
        return(this.conf);
    }

  // KEEPER
    protected TokenKeeper tokenKeeper;

    public TokenKeeper getTokenKeeper()
    {
        return(this.tokenKeeper);
    }

    /** 如果需要从工厂生成 mp请求(包括使用mp-servlet的默认实现), 且构造方法中未传入 appid 和 secret,
     * 则需要以此方法手动初始化 TokenKeeper. 带参的构造方法会尝试在配置完成后调用这个方法, 如果传参
     * 包含 appid 和 secret 则无需再手动配置.
     * TokenKeeper 为所有请求及默认实现的 servlet 保持 access token 和 jsapi ticket 的缓存及刷新服务.
     * 必需在 servlet 初始化前完成对这个方法的调用, 并且在 WxmpFactory 生命周期中只调用一次.
     */
    public WxmpFactory initTokenKeeper(String appid, String secret)
    {
        this.tokenKeeper = TokenKeeper.getInstance(appid, secret);

        return(this);
    }

    /** Initialize TokenKeeper according to <code>conf</code>.
     * Invoke once only.
     * Auto invoked by <code>new WxmpFactory(Properties conf)</code> and <code>new WxmpFactory(String resource)</code>.
     */
    public WxmpFactory initTokenKeeper()
    {
        this.initTokenKeeper(
            this.conf.getProperty(KEY_APPID),
            this.conf.getProperty(KEY_SECRET)
        );

        return(this);
    }

    /** Initialize WxmpRequestBase (loading certificates) according to <code>conf</code>.
     * Invoke once only.
     * Auto invoked by <code>new WxmpFactory(Properties conf)</code> and <code>new WxmpFactory(String resource)</code>.
     */
    public WxmpFactory initRequestBase()
    {
        WxmpRequestBase.configDefaultHC(
            new CertificateLoader().config(this.conf).asSSLContext()
        );

        return(this);
    }

  // CONSTRUCT
    /** Construct a new instance with blank config.
     */
    public WxmpFactory()
    {
        this.conf = new Properties();

        return;
    }

    /** Construct a new instance with a prepared config prop.
     */
    public WxmpFactory(Properties aConf)
    {
        this.conf = aConf;

        this.initTokenKeeper();
        this.initRequestBase();

        return;
    }

    /** Construct a new instance using a resource indicated by <code>resource</code>.
     */
    public WxmpFactory(String resource)
        throws MissingResourceException
    {
        this();

        try
        {
            this.conf.load(
                new InputStreamReader(
                    WxmpFactory.class.getResourceAsStream(resource),
                    "utf-8"
            ));
        }
        catch (Exception ex)
        {
            MissingResourceException mrex = new MissingResourceException(
                "Failed to load conf resource.",
                WxmpFactory.class.getName(),
                resource
            );
            mrex.initCause(ex);

            throw(mrex);
        }

        this.initTokenKeeper();
        this.initRequestBase();

        return;
    }

  // SINGLETON
    private static class Singleton
    {
        public static final WxmpFactory instance = new WxmpFactory(WxmpFactory.RESOURCE_WXPAY_PROPERTIES);
    }

    /** return default instance which load config from <code>/wxpay.properties</code>.
     * If you are binding multi-instance of WxmpFactory in your application, DO NOT use this method.
     */
    public static WxmpFactory getDefaultInstance()
    {
        return(Singleton.instance);
    }

    /** @deprecated Please use <code>getDefaultInstance()</code> instead.
     * This method now forwarded to <code>getDefaultInstance()</code>
     */
    public static WxmpFactory getInstance()
    {
        return(
            getDefaultInstance()
        );
    }

  // FACTORY
    /** Instantiate and fill default conf.
     * @param clazz Subclass of WxmpRequestBase, must implement constructor `clazz(Properties)`.
     */
    public WxmpRequestBase instantiate(Class<? extends WxmpRequestBase> clazz)
    {
        try
        {
            return(
                clazz.getConstructor(Properties.class).newInstance(new Properties(this.conf))
            );
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw(new RuntimeException(ex.getMessage(), ex));
        }
    }

    public WxmpRequestBase instantiateWithToken(Class<? extends WxmpRequestBase> clazz)
    {
        WxmpRequestBase req = this.instantiate(clazz);

        req.setProperty(KEY_ACCESS_TOKEN, this.getTokenKeeper().getAccessToken());

        return(req);
    }

    public TokenClientCredential newTokenClientCredential()
    {
        return(
            new TokenClientCredential(
                new Properties(this.conf)
        ));
    }
    public TokenClientCredential newTokenClientCredential(Properties conf)
    {
        return(
            new TokenClientCredential(
                buildConf(conf, this.conf)
        ));
    }

  // MISC
    protected static Properties buildConf(Properties prop, Properties defaults)
    {
        Properties ret = new Properties(defaults);
        Iterator<String> iter = prop.stringPropertyNames().iterator();
        while (iter.hasNext())
        {
            String key = iter.next();
            ret.setProperty(key, prop.getProperty(key));
        }

        return(ret);
    }
}
