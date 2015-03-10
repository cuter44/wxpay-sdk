package com.github.cuter44.wxpay.resps;

import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import com.alibaba.fastjson.*;

public class SnsUserinfoResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_OPENID       = "openid";
    public static final String KEY_NICKNAME     = "nickname";
    public static final String KEY_HEADIMGURL   = "headimgurl";

  // CONSTRUCT
    public SnsUserinfoResponse(String jsonString)
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

    public String getNickname()
    {
        return(
            this.getProperty(KEY_NICKNAME)
        );
    }

    public String getHeadimgurl()
    {
        return(
            this.getProperty(KEY_HEADIMGURL)
        );
    }

    public BufferedImage getHeadimg()
        throws IOException
    {
        try
        {
            return(
                ImageIO.read(new URL(this.getHeadimgurl()))
            );
        }
        catch (MalformedURLException ex)
        {
            throw(new IllegalArgumentException(ex));
        }
    }
}
