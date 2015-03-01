package com.github.cuter44.wxpay.resps;

import com.alibaba.fastjson.*;

public class SnsOAuthAccessTokenResponse
{
    public static final String KEY_OPENID       = "openid";
    public static final String KEY_ACCESS_TOKEN = "access_token";

    public JSONObject json;

    public SnsOAuthAccessTokenResponse(String jsonString)
    {
        this.json = JSON.parseObject(jsonString);

        return;
    }

    public String getProperty(String key)
    {
        return(
            this.json.getString(key)
        );
    }

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
