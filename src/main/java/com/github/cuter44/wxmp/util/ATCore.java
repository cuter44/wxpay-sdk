package com.github.cuter44.wxmp.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.alibaba.fastjson.*;

/** A token provider that keep an appid and secret and retrieve access token from wx server on its own.
 *  ... and auto renew on expired.
 *
 * It is not saying Absolute Terror Core, and absolutely not relevant to Absolute Terror Field and/or EVA.
 *
 * @since 0.9.2
 */
public class ATCore
    implements TokenProvider
{
    public static final String KEY_APPID    = "appid";
    public static final String KEY_SECRET   = "SECRET";
  // CONSTRUCT
    protected String appid;
    protected String secret;

    protected String accessToken;
    protected long atExpiration;

    protected String jssdkTicket;
    protected long jtExpiration;

    protected ATCore()
    {
        return;
    }

    protected ATCore(String appid, String secret)
    {
        this.config(appid, secret);

        return;
    }

    @Override
    public ATCore config(Properties p)
    {
        this.config(
            p.getProperty(KEY_APPID),
            p.getProperty(KEY_SECRET)
        );

        return(this);
    }

    public ATCore config(String appid, String secret)
    {
        this.appid = appid;
        this.secret = secret;
        this.atExpiration = 0L;
        this.jtExpiration = 0L;

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
            TokenClientCredentialResponse resp = new TokenClientCredential(this.appid, this.secret)
                .execute();

            this.accessToken = resp.getAccessToken();
            this.atExpiration = resp.getTmCreate() + (resp.getExpiresIn()*1000L);

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
    public ATCore forceRetrieveAccessToken()
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
            JSSDKGetticketResponse resp = new JSSDKGetticket(this.getAccessToken())
                .execute();

            this.jssdkTicket = resp.getTicket();
            this.jtExpiration = resp.getTmCreate() + (resp.getExpiresIn()*1000L);

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
    public ATCore forceRetrieveJSSDKTicket()
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
