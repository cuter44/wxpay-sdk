package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;

/** 第四步：拉取用户信息(需scope为 snsapi_userinfo)
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html#.E7.AC.AC.E5.9B.9B.E6.AD.A5.EF.BC.9A.E6.8B.89.E5.8F.96.E7.94.A8.E6.88.B7.E4.BF.A1.E6.81.AF.28.E9.9C.80scope.E4.B8.BA_snsapi_userinfo.29">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
    openid          用户的唯一标识
    lang            返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
 * </pre>
 */
public class SnsUserinfo extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token", "openid", "lang"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_OPENID        = "openid";
    public static final String KEY_LANG          = "lang";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/sns/userinfo";

  // CONSTRUCT
    public SnsUserinfo(Properties prop)
    {
        super(prop);

        if (super.getProperty(KEY_LANG) == null)
            super.setProperty(KEY_LANG, "zh_CN");

        return;
    }

    public SnsUserinfo(String accessToken, String openid)
    {
        super(new Properties());

        this.setProperty(KEY_ACCESS_TOKEN   , accessToken   )
            .setProperty(KEY_OPENID         , openid        )
            .setProperty(KEY_LANG           , "zh_CN"       );

        return;
    }

    public SnsUserinfo(SnsOAuthAccessTokenResponse resp)
    {
        super(new Properties());

        super.setProperty(KEY_ACCESS_TOKEN  , resp.getAccessToken() )
            .setProperty(KEY_OPENID         , resp.getOpenid()      )
            .setProperty(KEY_LANG           , "zh_CN"               );

        return;
    }

  // BUILD
    @Override
    public SnsUserinfo build()
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
    public SnsUserinfoResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new SnsUserinfoResponse(respJson));
    }

  // MISC
    public SnsUserinfo setOpenid(String openid)
    {
        super.setProperty(KEY_OPENID, openid);

        return(this);
    }

}
