package com.github.cuter44.wxcp.reqs;

import java.util.List;
import java.util.Arrays;
import java.util.Properties;
import java.io.IOException;

import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxcp.*;
import com.github.cuter44.wxcp.resps.*;

public class GetJsapiTicket extends WxcpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token", "type"
    );

    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String URL_API_BASE = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";

  // CONF
    protected String accessToken;

  // CONSTRUCT
    public GetJsapiTicket(String accessToken)
    {
        super(new Properties());

        this.accessToken = accessToken;

        return;
    }

    public GetJsapiTicket setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

  // BUILD
    @Override
    public GetJsapiTicket build()
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
    public GetJsapiTicketResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new GetJsapiTicketResponse(respJson));
    }
}
