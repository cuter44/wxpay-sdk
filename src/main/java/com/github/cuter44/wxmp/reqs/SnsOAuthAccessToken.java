package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

//import com.github.cuter44.nyafx.text.*;
//import com.alibaba.fastjson.*;

//import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.resps.*;

/** 第二步：通过code换取网页授权access_token
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.80.9A.E8.BF.87code.E6.8D.A2.E5.8F.96.E7.BD.91.E9.A1.B5.E6.8E.88.E6.9D.83access_token">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    appid       是      公众号的唯一标识
    secret      是      公众号的appsecret
    code        是      填写第一步获取的code参数
    <i>grant_type  是      填写为authorization_code</i>
 * </pre>
 */
public class SnsOAuthAccessToken extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "appid", "secret", "code", "grant_type"
    );

    public static final String KEY_APPID     = "appid";
    public static final String KEY_SECRET    = "secret";
    public static final String KEY_CODE      = "code";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/sns/oauth2/access_token";

  // CONSTRUCT
    public SnsOAuthAccessToken(Properties prop)
    {
        super(prop);

        super.setProperty("grant_type", "authorization_code");

        if (super.getProperty(KEY_SECRET) == null)
            super.setProperty(
                KEY_SECRET,
                super.getProperty("SECRET")
            );

        return;
    }

    public SnsOAuthAccessToken(String appid, String secret, String code)
    {
        super(new Properties());

        super.setProperty(KEY_APPID     , appid                 )
            .setProperty(KEY_SECRET     , secret                )
            .setProperty(KEY_CODE       , code                  )
            .setProperty("grant_type"   , "authorization_code"  );

        return;
    }

    public SnsOAuthAccessToken setAppid(String appid)
    {
        super.setProperty(KEY_APPID, appid);

        return(this);
    }

    public SnsOAuthAccessToken setSecret(String secret)
    {
        super.setProperty(KEY_SECRET, secret);

        return(this);
    }

    public SnsOAuthAccessToken setCode(String code)
    {
        super.setProperty(KEY_CODE, code);

        return(this);
    }


  // BUILD
    @Override
    public SnsOAuthAccessToken build()
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
    public SnsOAuthAccessTokenResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new SnsOAuthAccessTokenResponse(respJson));
    }
}
