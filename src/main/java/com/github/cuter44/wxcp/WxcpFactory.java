package com.github.cuter44.wxcp;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.MissingResourceException;

import com.github.cuter44.wxmp.util.CertificateLoader;
import com.github.cuter44.wxcp.reqs.*;
import com.github.cuter44.wxcp.util.*;

/** 微信CP工厂
 * <br />
 * ...没错这个就是从MP复制过来的.
 */
public class WxcpFactory
{
  // CONSTANT
    private static final String RESOURCE_WXCP_PROPERTIES = "/wxcp.properties";

    protected static final String KEY_CORPID = "corpid";
    protected static final String KEY_CORPSECRET = "corpsecret";

    protected static final String KEY_ACCESS_TOKEN = "access_token";

  // CONFIG
    protected Properties conf;

    public Properties getConf()
    {
        return(this.conf);
    }

  // KEEPER
    protected WxcpTokenKeeper tokenKeeper;

    public WxcpTokenKeeper getWxcpTokenKeeper()
    {
        return(this.tokenKeeper);
    }

    /** 如果需要从工厂生成 mp请求(包括使用mp-servlet的默认实现), 且构造方法中未传入 corpid 和 corpscrect,
     * 则需要以此方法手动初始化 WxcpTokenKeeper. 带参的构造方法会尝试在配置完成后调用这个方法, 如果传参
     * 包含 corpid 和 corpscrect 则无需再手动配置.
     * WxcpTokenKeeper 为所有请求及默认实现的 servlet 保持 access token 和 jsapi ticket 的缓存及刷新服务.
     * 必需在 servlet 初始化前完成对这个方法的调用, 并且在 WxcpFactory 生命周期中只调用一次.
     */
    public WxcpFactory initWxcpTokenKeeper(String corpid, String corpscrect)
    {
        this.tokenKeeper = WxcpTokenKeeper.getInstance(corpid, corpscrect);

        return(this);
    }

    /** Initialize WxcpTokenKeeper according to <code>conf</code>.
     * Invoke once only.
     * Auto invoked by <code>new WxcpFactory(Properties conf)</code> and <code>new WxcpFactory(String resource)</code>.
     */
    public WxcpFactory initWxcpTokenKeeper()
    {
        this.initWxcpTokenKeeper(
            this.conf.getProperty(KEY_CORPID),
            this.conf.getProperty(KEY_CORPSECRET)
        );

        return(this);
    }

    /** Initialize WxcpRequestBase (loading certificates) according to <code>conf</code>.
     * Invoke once only.
     * Auto invoked by <code>new WxcpFactory(Properties conf)</code> and <code>new WxcpFactory(String resource)</code>.
     */
    public WxcpFactory initRequestBase()
    {
        WxcpRequestBase.configDefaultHC(
            new CertificateLoader().config(this.conf).asSSLContext()
        );

        return(this);
    }

  // CONSTRUCT
    /** Construct a new instance with blank config.
     */
    public WxcpFactory()
    {
        this.conf = new Properties();

        return;
    }

    /** Construct a new instance with a prepared config prop.
     */
    public WxcpFactory(Properties aConf)
    {
        this.conf = aConf;

        this.initWxcpTokenKeeper();
        this.initRequestBase();

        return;
    }

    /** Construct a new instance using a resource indicated by <code>resource</code>.
     */
    public WxcpFactory(String resource)
        throws MissingResourceException
    {
        this();

        try
        {
            this.conf.load(
                new InputStreamReader(
                    WxcpFactory.class.getResourceAsStream(resource),
                    "utf-8"
            ));
        }
        catch (Exception ex)
        {
            MissingResourceException mrex = new MissingResourceException(
                "Failed to load conf resource.",
                WxcpFactory.class.getName(),
                resource
            );
            mrex.initCause(ex);

            throw(mrex);
        }

        this.initWxcpTokenKeeper();
        this.initRequestBase();

        return;
    }

  // SINGLETON
    private static class Singleton
    {
        public static final WxcpFactory instance = new WxcpFactory(WxcpFactory.RESOURCE_WXCP_PROPERTIES);
    }

    /** return default instance which load config from <code>/wxpay.properties</code>.
     * If you are binding multi-instance of WxcpFactory in your application, DO NOT use this method.
     */
    public static WxcpFactory getDefaultInstance()
    {
        return(Singleton.instance);
    }

    /** @deprecated Please use <code>getDefaultInstance()</code> instead.
     * This method now forwarded to <code>getDefaultInstance()</code>
     */
    public static WxcpFactory getInstance()
    {
        return(
            getDefaultInstance()
        );
    }

  // FACTORY
    /** Instantiate and fill default conf.
     * @param clazz Subclass of WxcpRequestBase, must implement constructor `clazz(Properties)`.
     */
    public WxcpRequestBase instantiate(Class<? extends WxcpRequestBase> clazz)
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

    public WxcpRequestBase instantiateWithToken(Class<? extends WxcpRequestBase> clazz)
    {
        WxcpRequestBase req = this.instantiate(clazz);

        req.setProperty(KEY_ACCESS_TOKEN, this.getWxcpTokenKeeper().getAccessToken());

        return(req);
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
