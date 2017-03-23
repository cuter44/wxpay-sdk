package com.github.cuter44.wxmp;

import java.io.Closeable;
import java.io.IOException;

import com.github.cuter44.wxmp.reqs.WxmpRequestBase;


/** Auto configurated, singleton WxmpFactory.
 * Read <code>/wxpay.properties</code> from runtime. Suitiable for single
 * wx-account used.
 */
public class WxmpFactorySingl extends WxmpFactoryBase
    implements Closeable
{
    private static final String RESOURCE_WXPAY_PROPERTIES = "/wxpay.properties";

  // CONSTRUCT
    private WxmpFactorySingl()
    {
        this.configFromFile(RESOURCE_WXPAY_PROPERTIES);
        this.setupHttpClientGlobal();
        this.initTokenProvider();

        // #2,#3 relies on #1, so #1 goes first.
        // #3 relies on #2
        //   , on using ATCore, order doesn't matter since ATCore is designed to
        //     init-on-demand.
        //   , but it is not a standard, better stick to this to be compatiable
        //     with other implemention.
    }

  // SINGLETON
    private static class Singleton
    {
        public static final WxmpFactorySingl instance = new WxmpFactorySingl();
    }

    /** Return singleton of WxmpFactorySingl.
     */
    public static WxmpFactorySingl getInstance()
    {
        return(Singleton.instance);
    }

  // FACTORY
    public <X extends WxmpRequestBase> X instantiateWithToken(Class<X> reqClass)
        throws Exception
    {
        return(
            this.setToken(
                this.instantiate(reqClass)
            )
        );
    }

  // CLOSE
    @Override
    public void close()
        throws IOException
    {
        WxmpRequestBase.defaultHttpClient.close();
        this.tokenProvider.close();

        return;
    }
}
