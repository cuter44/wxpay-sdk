package com.github.cuter44.wxmp.resps;

import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import com.alibaba.fastjson.*;

/** 附录1-JS-SDK使用权限签名算法
 * <br />
 * (No specification provided in official wiki)
 */
public class JSSDKGetticketResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_TICKET       = "ticket";
    public static final String KEY_EXPIRES_IN   = "expires_in";

  // CONSTRUCT
    /** @return Creating time of this object.
     */
    public final long tmCreate = System.currentTimeMillis();

    public JSSDKGetticketResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public String getTicket()
    {
        return(
            super.getProperty(KEY_TICKET)
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
