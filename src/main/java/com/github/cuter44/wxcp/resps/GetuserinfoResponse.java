package com.github.cuter44.wxcp.resps;

import com.alibaba.fastjson.*;

/** 根据code获取成员信息
 * <br />
 * <pre style="font-size:12px">
    返回说明
    UserId      成员UserID
    OpenId      非企业成员的标识，对当前企业号唯一
    DeviceId    凭证有效时间，单位：秒
 * </pre>
 */
public class GetuserinfoResponse extends WxcpResponseBase
{
  // CONSTANTS
    public static final String KEY_USER_ID      = "UserId";
    public static final String KEY_OPEN_ID      = "Openid";
    public static final String KEY_DEVICE_ID    = "DeviceId";

  // CONSTRUCT
    public GetuserinfoResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public final boolean isCorpMember()
    {
        return(
            this.getUserId() != null
        );
    }

    public String getUserId()
    {
        return(
            super.getProperty(KEY_USER_ID)
        );
    }

    public String getOpenId()
    {
        return(
            super.getProperty(KEY_OPEN_ID)
        );
    }

    public String getDeviceId()
    {
        return(
            getProperty(KEY_DEVICE_ID)
        );
    }
}
