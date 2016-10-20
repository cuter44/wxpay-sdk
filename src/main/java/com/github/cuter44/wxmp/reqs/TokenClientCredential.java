package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;

/** 获取access token
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    <i>grant_type  是      获取access_token填写client_credential</i>
    appid       是      第三方用户唯一凭证
    secret      是      第三方用户唯一凭证密钥，即appsecret
 * </pre>
 */
public class TokenClientCredential extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "grant_type", "appid", "secret"
    );

    public static final String KEY_APPID         = "appid";
    public static final String KEY_SECRET        = "secret";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/token";

  // CONSTRUCT
    public TokenClientCredential(Properties prop)
    {
        super(prop);

        super.setProperty("grant_type", "client_credential");

        if (super.getProperty(KEY_SECRET) == null)
            super.setProperty(
                KEY_SECRET,
                super.getProperty("SECRET")
            );

        return;
    }

    public TokenClientCredential(String appid, String secret)
    {
        super(new Properties());

        super.setProperty("grant_type"  , "client_credential"   )
            .setProperty(KEY_APPID      , appid                 )
            .setProperty(KEY_SECRET     , secret                );

        return;
    }

    public TokenClientCredential setAppid(String appid)
    {
        super.setProperty(KEY_APPID, appid);

        return(this);
    }

    public TokenClientCredential setSecret(String secret)
    {
        super.setProperty(KEY_SECRET, secret);

        return(this);
    }


  // BUILD
    @Override
    public TokenClientCredential build()
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
    public TokenClientCredentialResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new TokenClientCredentialResponse(respJson));
    }
}
