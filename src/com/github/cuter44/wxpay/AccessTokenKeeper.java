package com.github.cuter44.wxpay;

import java.io.IOException;

import com.github.cuter44.wxpay.reqs.*;
import com.github.cuter44.wxpay.resps.*;
import com.alibaba.fastjson.*;

/** Access token keeper
 * Keep minding the access token and expiration, and automatically refresh if expired.
 */
public class AccessTokenKeeper
{
  // CONSTRUCT
    protected String appid;
    protected String secret;
    protected long expiration;
    protected String accessToken;

    public AccessTokenKeeper(String appid, String secret)
    {
        this.expiration = 0L;
        this.appid = appid;
        this.secret = secret;

        return;
    }

    protected void retrieveAccessToken()
    {
        try
        {
            TokenClientCredentialResponse resp = new TokenClientCredential(this.appid, this.secret)
                .execute();

            this.accessToken = resp.getAccessToken();
            this.expiration = resp.getTmCreate() + (resp.getExpiresIn()*1000L);

            return;
        }
        catch (IOException ex)
        {
            throw(new RuntimeException(ex));
        }
    }

    public void forceRetrieveToken()
    {
        this.retrieveAccessToken();

        return;
    }

    public String getAccessToken()
    {
        if (this.expiration > System.currentTimeMillis())
            return(this.accessToken);

        // else
        synchronized(this)
        {
            if (this.expiration > System.currentTimeMillis())
                return(this.accessToken);

            this.retrieveAccessToken();

            return(this.accessToken);
        }
    }
}
