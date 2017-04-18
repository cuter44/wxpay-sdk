package com.github.cuter44.wxcp.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.wxcp.reqs.*;
import com.github.cuter44.wxcp.resps.*;
import com.alibaba.fastjson.*;

/** A token provider that keep an corpid and corpsecret and retrieve access token from wx server on its own.
 *  ... and auto renew on expired.
 *
 * It is not saying Absolute Terror Core, and absolutely not relevant to Absolute Terror Field and/or EVA.
 *
 * @since 0.9.2
 */
public class ATCoreCp
    implements TokenProviderCp
{
    public static final String KEY_CORPID       = "corpid";
    public static final String KEY_CORPSECRET   = "corpsecret";
  // CONSTRUCT
    protected String corpid;
    protected String corpsecret;

    protected String accessToken;
    protected long atExpiration;

    protected String jssdkTicket;
    protected long jtExpiration;

    protected ATCoreCp()
    {
        return;
    }

    protected ATCoreCp(String corpid, String corpsecret)
    {
        this.config(corpid, corpsecret);

        return;
    }

    @Override
    public ATCoreCp config(Properties p)
    {
        this.config(
            p.getProperty(KEY_CORPID),
            p.getProperty(KEY_CORPSECRET)
        );

        return(this);
    }

    public ATCoreCp config(String corpid, String corpsecret)
    {
        this.corpid = corpid;
        this.corpsecret = corpsecret;
        this.atExpiration = 0L;
        this.jtExpiration = 0L;

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
            GettokenResponse resp = new Gettoken(this.corpid, this.corpsecret)
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
    public ATCoreCp forceRetrieveAccessToken()
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
    protected void retrieveJsapiTicket()
    {
        try
        {
            GetJsapiTicketResponse resp = new GetJsapiTicket(this.getAccessToken())
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
    public ATCoreCp forceRetrieveJsapiTicket()
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
