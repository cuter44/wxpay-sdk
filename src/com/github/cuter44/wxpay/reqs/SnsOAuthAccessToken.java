package com.github.cuter44.wxpay.reqs;

import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.nyafx.text.*;
import org.apache.http.client.fluent.*;

import com.github.cuter44.wxpay.*;
import com.github.cuter44.wxpay.constants.*;
import com.github.cuter44.wxpay.resps.*;

public class SnsOAuthAccessToken
{
  // KEYS
    public static final String URL_API_BASE = "https://api.weixin.qq.com/sns/oauth2/access_token";

  // CONF
    protected String appid;
    protected String secret;
    protected String code;

  // CONSTRUCT
    public SnsOAuthAccessToken(String appid, String secret, String code)
    {
        if (appid == null)
            throw(new NullPointerException("Null appid."));
        this.appid = appid;

        if (secret == null)
            throw(new NullPointerException("Null secret."));
        this.secret = secret;

        if (code == null)
            throw(new NullPointerException("Null code."));
        this.code = code;

        return;
    }

  // EXECUTE
    public SnsOAuthAccessTokenResponse execute()
        throws IOException
    {
        String url = new URLBuilder()
            .appendPath(URL_API_BASE)
            .appendParam("appid"        , this.appid)
            .appendParam("secret"       , this.secret)
            .appendParam("code"         , this.code)
            .appendParam("grant_type"   , "authorization_code")
            .toString();

        String strJson = Request.Get(url)
            .execute()
            .returnContent()
            .asString();

        return(new SnsOAuthAccessTokenResponse(strJson));
    }

}
