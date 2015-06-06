package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

/** 第二步：通过code换取网页授权access_token
 *
 * <pre style="font-size:12px">
    返回说明
    access_token    网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
    expires_in      access_token接口调用凭证超时时间，单位（秒）
    refresh_token   用户刷新access_token
    openid          用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
    scope           用户授权的作用域，使用逗号（,）分隔
    unionid         只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
 * </pre>
 */
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
            super.getProperty(KEY_OPENID)
        );
    }

    public String getAccessToken()
    {
        return(
            super.getProperty(KEY_ACCESS_TOKEN)
        );
    }
}
