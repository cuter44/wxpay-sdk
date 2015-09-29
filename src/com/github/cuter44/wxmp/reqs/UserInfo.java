package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;

/** 获取用户基本信息(UnionID机制)
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    openid          普通用户的标识，对当前公众号唯一
    lang            返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
 * </pre>
 */
public class UserInfo extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token", "openid", "lang"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_OPENID        = "openid";
    public static final String KEY_LANG          = "lang";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/user/info";

  // CONSTRUCT
    public UserInfo(Properties prop)
    {
        super(prop);

        if (super.getProperty(KEY_LANG) == null)
            super.setProperty(KEY_LANG, "zh_CN");

        return;
    }

    public UserInfo(String accessToken, String openid)
    {
        super(new Properties());

        this.setProperty(KEY_ACCESS_TOKEN   , accessToken   )
            .setProperty(KEY_OPENID         , openid        )
            .setProperty(KEY_LANG           , "zh_CN"       );

        return;
    }

    public UserInfo(SnsOAuthAccessTokenResponse resp)
    {
        super(new Properties());

        super.setProperty(KEY_ACCESS_TOKEN  , resp.getAccessToken() )
            .setProperty(KEY_OPENID         , resp.getOpenid()      )
            .setProperty(KEY_LANG           , "zh_CN"               );

        return;
    }

  // BUILD
    @Override
    public UserInfo build()
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
    public UserInfoResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new UserInfoResponse(respJson));
    }

  // MISC
    public UserInfo setOpenid(String openid)
    {
        super.setProperty(KEY_OPENID, openid);

        return(this);
    }

}
