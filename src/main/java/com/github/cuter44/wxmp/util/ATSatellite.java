package com.github.cuter44.wxmp.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.alibaba.fastjson.*;

/** A token provider that retrive access_token from specified upstream.
 * ... and auto renew on expired. This is designed to plug with ATDistribute.
 *
 * This implemention do not send secret to network. It need secret only to
 * feature getSecret(), since other feature such as Snsapi will ask here for
 * secret.
 *
 * It is not saying Absolute Terror Satellite, and absolutely not relevant to Absolute Terror Field and/or EVA.
 *
 * @since 0.9.2
 */
public class ATSatellite
    implements TokenProvider
{
    public static final String KEY_APPID        = "appid";
    public static final String KEY_SECRET       = "SECRET";
    public static final String KEY_AT_UPSTREAM  = "AT_UPSTREAM";
    public static final String KEY_JT_UPSTREAM  = "JT_UPSTREAM";
  // CONSTRUCT
    protected String atUpstreamURL;
    protected String jtUpstreamURL;
    protected String appid;
    protected String secret;

    protected String accessToken;
    protected long atExpiration;

    protected String jssdkTicket;
    protected long jtExpiration;

    protected ATSatellite()
    {
        return;
    }

    protected ATSatellite(String appid, String secret, String atUpstreamURL, String jtUpstreamURL)
    {
        this.config(appid, secret, atUpstreamURL, jtUpstreamURL);

        return;
    }

    @Override
    public ATSatellite config(Properties p)
    {
        this.config(
            p.getProperty(KEY_APPID),
            p.getProperty(KEY_SECRET),
            p.getProperty(KEY_AT_UPSTREAM),
            p.getProperty(KEY_JT_UPSTREAM)
        );

        return(this);
    }

    public ATSatellite config(String appid, String secret, String atUpstreamURL, String jtUpstreamURL)
    {

        this.appid          = appid;
        this.secret         = secret;
        this.atUpstreamURL  = atUpstreamURL;
        this.jtUpstreamURL  = jtUpstreamURL;
        this.atExpiration   = 0L;
        this.jtExpiration   = 0L;

        return(this);
    }

    @Override
    public String getAppid()
    {
        return(this.appid);
    }

    @Override
    public String getSecret()
    {
        return(this.secret);
    }


  // ACCESS_TOKEN
    protected void retrieveAccessToken()
    {
        try
        {
            TokenClientRelayResponse resp = new TokenClientRelay(this.appid, this.atUpstreamURL)
                .execute();

            this.accessToken = resp.getAccessToken();
            this.atExpiration = resp.getExpires();

            return;
        }
        catch (IOException ex)
        {
            throw(new RuntimeException(ex));
        }
    }

    /** @return this
     */
    @Override
    public ATSatellite forceRetrieveAccessToken()
    {
        this.retrieveAccessToken();

        return(this);
    }

    @Override
    public String getAccessToken()
    {
        if (this.atExpiration > System.currentTimeMillis())
            return(this.accessToken);

        // else
        synchronized(this)
        {
            if (this.atExpiration > System.currentTimeMillis())
                return(this.accessToken);

            this.retrieveAccessToken();

            return(this.accessToken);
        }
    }

    @Override
    public long getATExpire()
    {
        return(this.atExpiration);
    }

  // JSSDK_TICKET
    protected void retrieveJSSDKTicket()
    {
        try
        {
            JSSDKTicketRelayResponse resp = new JSSDKTicketRelay(this.appid, this.jtUpstreamURL)
                .execute();

            this.jssdkTicket = resp.getTicket();
            this.jtExpiration = resp.getExpires();

            return;
        }
        catch (IOException ex)
        {
            throw(new RuntimeException(ex));
        }
    }

    /** @return this
     */
    @Override
    public ATSatellite forceRetrieveJSSDKTicket()
    {
        this.retrieveJSSDKTicket();

        return(this);
    }

    @Override
    public String getJSSDKTicket()
    {
        if (this.jtExpiration > System.currentTimeMillis())
            return(this.jssdkTicket);

        // else
        synchronized(this)
        {
            if (this.jtExpiration > System.currentTimeMillis())
                return(this.jssdkTicket);

            this.retrieveJSSDKTicket();

            return(this.jssdkTicket);
        }
    }

    @Override
    public long getJTExpire()
    {
        return(this.jtExpiration);
    }

    @Override
    public void close()
    {
        // NOOP

        return;
    }
}
