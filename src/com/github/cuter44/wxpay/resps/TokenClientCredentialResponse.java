package com.github.cuter44.wxpay.resps;

import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import com.alibaba.fastjson.*;

public class TokenClientCredentialResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_EXPIRES_IN   = "expires_in";

  // CONSTRUCT
    /** @return Creating time of this object.
     */
    public final long tmCreate = System.currentTimeMillis();

    public TokenClientCredentialResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public String getAccessToken()
    {
        return(
            this.getProperty(KEY_ACCESS_TOKEN)
        );
    }

    /** @return expires_in is in second
     */
    public Long getExpiresIn()
    {
        return(
            this.json.getLong(KEY_EXPIRES_IN)
        );
    }

    public long getTmCreate()
    {
        return(this.tmCreate);
    }

}
