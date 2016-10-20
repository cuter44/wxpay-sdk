package com.github.cuter44.wxmp.reqs;

import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.resps.*;

public class JSSDKGetticket extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token", "type"
    );

    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

  // CONSTRUCT
    public JSSDKGetticket(String accessToken)
    {
        super(new Properties());

        this.setProperty("type", "jsapi");
        this.setAccessToken(accessToken);

        return;
    }

    public JSSDKGetticket setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

  // BUILD
    @Override
    public JSSDKGetticket build()
    {
        return(this);
    }

  // TO_URL
    @Override
    public String toURL()
    {
        throw(
            new UnsupportedOperationException("This request does not execute on client side.")
        );
    }
  // EXECUTE
    @Override
    public JSSDKGetticketResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new JSSDKGetticketResponse(respJson));
    }
}
