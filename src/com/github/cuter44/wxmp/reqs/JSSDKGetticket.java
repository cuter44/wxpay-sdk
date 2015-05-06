package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.nyafx.text.*;
import org.apache.http.client.fluent.*;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.resps.*;

public class JSSDKGetticket
{
  // KEYS
    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

  // CONF
    protected String accessToken;

  // CONSTRUCT
    public JSSDKGetticket(String accessToken)
    {
        this.accessToken = accessToken;

        return;
    }

  // EXECUTE
    public JSSDKGetticketResponse execute()
        throws IOException
    {
        String url = new URLBuilder()
            .appendPath(URL_API_BASE)
            .appendParam("access_token" , this.accessToken)
            .appendParam("type"         , "jsapi")
            .toString();

        String respJson = new String(
            Request.Get(url)
                .execute()
                .returnContent()
                .asBytes(),
            "utf-8"
        );

        return(new JSSDKGetticketResponse(respJson));
    }

}
