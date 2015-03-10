package com.github.cuter44.wxpay.resps;

import com.alibaba.fastjson.*;

public class SnsOAuthAccessTokenResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_OPENID       = "openid";
    public static final String KEY_ACCESS_TOKEN = "access_token";

  // CONSTRUCT
    public SnsOAuthAccessTokenResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public String getOpenid()
    {
        return(
            this.getProperty(KEY_OPENID)
        );
    }

    public String getAccessToken()
    {
        return(
            this.getProperty(KEY_ACCESS_TOKEN)
        );
    }
}
