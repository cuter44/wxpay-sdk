package com.github.cuter44.wxpay;

import java.io.IOException;

import com.github.cuter44.wxpay.reqs.*;
import com.github.cuter44.wxpay.resps.*;
import com.alibaba.fastjson.*;

/** Token keeper
 * Keep minding the access token and JSSDK Ticket and their expiration, and automatically refresh if expired.
 */
public class TokenKeeper
{
  // CONSTRUCT
    protected String appid;
    protected String secret;

    protected String accessToken;
    protected long atExpiration;

    protected String jssdkTicket;
    protected long jtExpiration;

    public TokenKeeper(String appid, String secret)
    {
        this.appid = appid;
        this.secret = secret;
        this.atExpiration = 0L;
        this.jtExpiration = 0L;

        return;
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
    public TokenKeeper forceRetrieveAccessToken()
    {
        this.retrieveAccessToken();

        return(this);
    }

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
    public TokenKeeper forceRetrieveJSSDKTicket()
    {
        this.retrieveJSSDKTicket();

        return(this);
    }

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
}
