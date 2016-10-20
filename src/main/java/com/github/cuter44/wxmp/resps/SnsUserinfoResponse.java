package com.github.cuter44.wxmp.resps;

import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import com.alibaba.fastjson.*;

/** 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 * <br />
 * <pre style="font-size:12px">
    返回说明
    openid      用户的唯一标识
    nickname    用户昵称
    sex         用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    province    用户个人资料填写的省份
    city        普通用户个人资料填写的城市
    country     国家，如中国为CN
    headimgurl  用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    privilege   用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    unionid     只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
 * </pre>
 */
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
            super.getProperty(KEY_OPENID)
        );
    }

    public String getNickname()
    {
        return(
            super.getProperty(KEY_NICKNAME)
        );
    }

    public String getHeadimgurl()
    {
        return(
            super.getProperty(KEY_HEADIMGURL)
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
