package com.github.cuter44.wxmp.resps;

import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import com.alibaba.fastjson.*;

/** 客服接口-发消息(需scope为 snsapi_userinfo)
 * <br />
 * (No specification provided in official wiki)
 */
public class MessageCustomSendResponse extends WxmpResponseBase
{
  // CONSTANTS

  // CONSTRUCT
    public MessageCustomSendResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
}
