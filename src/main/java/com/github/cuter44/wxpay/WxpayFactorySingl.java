package com.github.cuter44.wxpay;

import java.io.Closeable;
import java.io.IOException;

import com.github.cuter44.wxpay.reqs.WxpayRequestBase;


/** Auto configurated, singleton WxmpFactory.
 * Read <code>/wxpay.properties</code> from runtime. Suitiable for single
 * wx-account used.
 */
public class WxpayFactorySingl extends WxpayFactoryBase
    implements Closeable
{
    private static final String RESOURCE_WXPAY_PROPERTIES = "/wxpay.properties";

  // CONSTRUCT
    private WxpayFactorySingl()
    {
        this.configFromFile(RESOURCE_WXPAY_PROPERTIES);
        this.setupHttpClientGlobal();

        return;
    }

  // SINGLETON
    private static class Singleton
    {
        public static final WxpayFactorySingl instance = new WxpayFactorySingl();
    }

    /** Return singleton of WxpayFactorySingl.
     */
    public static WxpayFactorySingl getInstance()
    {
        return(Singleton.instance);
    }

  // CLOSE
    @Override
    public void close()
        throws IOException
    {
        WxpayRequestBase.defaultHttpClient.close();

        return;
    }
}
