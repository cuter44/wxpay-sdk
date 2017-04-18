package com.github.cuter44.wxcp;

import java.io.Closeable;
import java.io.IOException;

import com.github.cuter44.wxcp.reqs.WxcpRequestBase;


/** Auto configurated, singleton WxmpFactory.
 * Read <code>/wxcp.properties</code> from runtime. Suitiable for single
 * wx-account used.
 */
public class WxcpFactorySingl extends WxcpFactoryBase
    implements Closeable
{
    private static final String RESOURCE_WXCP_PROPERTIES = "/wxcp.properties";

  // CONSTRUCT
    private WxcpFactorySingl()
    {
        this.configFromFile(RESOURCE_WXCP_PROPERTIES);
        this.setupHttpClientGlobal();
        this.initTokenProvider();

        // #2,#3 relies on #1, so #1 goes first.
        // #3 relies on #2
        //   , on using ATCoreCp, order doesn't matter since ATCoreCp is designed to
        //     init-on-demand.
        //   , but it is not a standard, better stick to this to be compatiable
        //     with other implemention.

        return;
    }

  // SINGLETON
    private static class Singleton
    {
        public static final WxcpFactorySingl instance = new WxcpFactorySingl();
    }

    /** Return singleton of WxcpFactorySingl.
     */
    public static WxcpFactorySingl getInstance()
    {
        return(Singleton.instance);
    }

  // FACTORY
    public <X extends WxcpRequestBase> X instantiateWithToken(Class<X> reqClass)
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
        WxcpRequestBase.defaultHttpClient.close();
        this.tokenProvider.close();

        return;
    }
}
