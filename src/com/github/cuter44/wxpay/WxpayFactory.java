package com.github.cuter44.wxpay;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.MissingResourceException;

import com.github.cuter44.wxpay.reqs.*;

/** 创建支付宝业务的工厂类
 */
public class WxpayFactory
{
  // CONSTANT
    private static final String RESOURCE_WXPAY_PROPERTIES = "/wxpay.properties";

  // CONFIG
    protected Properties conf;

    public Properties getConf()
    {
        return(this.conf);
    }

  // CONSTRUCT
    /** Construct a new instance with blank config.
     */
    public WxpayFactory()
    {
        this.conf = new Properties();

        return;
    }

    /** Construct a new instance using a resource indicated by <code>resource</code>.
     */
    public WxpayFactory(String resource)
        throws MissingResourceException
    {
        this();

        try
        {
            this.conf.load(
                new InputStreamReader(
                    WxpayFactory.class.getResourceAsStream(resource),
                    "utf-8"
            ));

            return;
        }
        catch (Exception ex)
        {
            MissingResourceException mrex = new MissingResourceException(
                "Failed to load conf resource.",
                WxpayFactory.class.getName(),
                resource
            );
            mrex.initCause(ex);

            throw(mrex);
        }
    }

    /** Construct a new instance with a prepared config prop.
     */
    public WxpayFactory(Properties aConf)
    {
        super();

        this.conf = aConf;

        return;
    }

  // SINGLETON
    private static class Singleton
    {
        public static final WxpayFactory instance = new WxpayFactory("/wxpay.properties");
    }

    /** return default instance which load config from <code>/wxpay.properties</code>.
     * If you are binding multi-instance of WxpayFactory in your application, DO NOT use this method.
     */
    public static WxpayFactory getDefaultInstance()
    {
        return(Singleton.instance);
    }

    /** @deprecated Please use <code>getDefaultInstance()</code> instead.
     * This method now forwarded to <code>getDefaultInstance()</code>
     */
    public static WxpayFactory getInstance()
    {
        return(
            getDefaultInstance()
        );
    }

  // FACTORY

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
