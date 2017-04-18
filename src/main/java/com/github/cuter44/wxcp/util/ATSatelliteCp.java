package com.github.cuter44.wxcp.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.wxcp.reqs.*;
import com.github.cuter44.wxcp.resps.*;
import com.alibaba.fastjson.*;

/** A token provider that retrive access_token from specified upstream.
 * ... and auto renew on expired. This is designed to plug with ATDistribute.
 *
 * This implemention do not send corpsecret to network. It need corpsecret only to
 * feature getSecret(), since other feature such as Snsapi will ask here for
 * corpsecret.
 *
 * It is not saying Absolute Terror Satellite, and absolutely not relevant to Absolute Terror Field and/or EVA.
 *
 * @since 0.9.2
 */
public class ATSatelliteCp
    implements TokenProviderCp
{
    public static final String KEY_CORPID           = "corpid";
    public static final String KEY_CORPSECRET       = "corpsecret";
    public static final String KEY_AT_UPSTREAM_CP   = "AT_UPSTREAM_CP";
    public static final String KEY_JT_UPSTREAM_CP   = "JT_UPSTREAM_CP";
  // CONSTRUCT
    protected String atUpstreamURL;
    protected String jtUpstreamURL;
    protected String corpid;
    protected String corpsecret;

    protected String accessToken;
    protected long atExpiration;

    protected String jssdkTicket;
    protected long jtExpiration;

    protected ATSatelliteCp()
    {
        return;
    }

    protected ATSatelliteCp(String corpid, String corpsecret, String atUpstreamURL, String jtUpstreamURL)
    {
        this.config(corpid, corpsecret, atUpstreamURL, jtUpstreamURL);

        return;
    }

    @Override
    public ATSatelliteCp config(Properties p)
    {
        this.config(
            p.getProperty(KEY_CORPID),
            p.getProperty(KEY_CORPSECRET),
            p.getProperty(KEY_AT_UPSTREAM_CP),
            p.getProperty(KEY_JT_UPSTREAM_CP)
        );

        return(this);
    }

    public ATSatelliteCp config(String corpid, String corpsecret, String atUpstreamURL, String jtUpstreamURL)
    {
        this.corpid         = corpid;
        this.corpsecret     = corpsecret;
        this.atUpstreamURL  = atUpstreamURL;
        this.jtUpstreamURL  = jtUpstreamURL;
        this.atExpiration   = 0L;
        this.jtExpiration   = 0L;

        return(this);
    }

    @Override
    public String getCorpid()
    {
        return(this.corpid);
    }

    @Override
    public String getCorpsecret()
    {
        return(this.corpsecret);
    }


  // ACCESS_TOKEN
    protected void retrieveAccessToken()
    {
        try
        {
            GettokenRelayResponse resp = new GettokenRelay(this.corpid, this.atUpstreamURL)
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
    public ATSatelliteCp forceRetrieveAccessToken()
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

  // Jsapi_TICKET
    protected void retrieveJsapiTicket()
    {
        try
        {
            GetJsapiTicketRelayResponse resp = new GetJsapiTicketRelay(this.corpid, this.jtUpstreamURL)
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
    public ATSatelliteCp forceRetrieveJsapiTicket()
    {
        this.retrieveJsapiTicket();

        return(this);
    }

    @Override
    public String getJsapiTicket()
    {
        if (this.jtExpiration > System.currentTimeMillis())
            return(this.jssdkTicket);

        // else
        synchronized(this)
        {
            if (this.jtExpiration > System.currentTimeMillis())
                return(this.jssdkTicket);

            this.retrieveJsapiTicket();

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
