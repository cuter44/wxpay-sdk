package com.github.cuter44.wxpay.reqs;

import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.nyafx.text.*;
import org.apache.http.client.fluent.*;

import com.github.cuter44.wxpay.*;
import com.github.cuter44.wxpay.constants.*;
import com.github.cuter44.wxpay.resps.*;
import static com.github.cuter44.wxpay.reqs.RequestBase.KEY_APPID;
import static com.github.cuter44.wxpay.reqs.RequestBase.KEY_SECRET;

public class TokenClientCredential
{
  // KEYS
    protected static final String KEY_APPID         = "appid";
    protected static final String KEY_SECRET        = "secret";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/token";

  // CONF
    protected String appid;
    protected String secret;

  // CONSTRUCT
    public TokenClientCredential(String appid, String secret)
    {
        this.appid = appid;
        this.secret = secret;

        return;
    }

  // EXECUTE
    public TokenClientCredentialResponse execute()
        throws IOException
    {
        String url = new URLBuilder()
            .appendPath(URL_API_BASE)
            .appendParam("appid"        , this.appid)
            .appendParam("secret"       , this.secret)
            .appendParam("grant_type"   , "client_credential")
            .toString();

        String respJson = new String(
            Request.Get(url)
                .execute()
                .returnContent()
                .asBytes(),
            "utf-8"
        );

        return(new TokenClientCredentialResponse(respJson));
    }

}
