package com.github.cuter44.wxcp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxcp.resps.*;

/** 获取access token
 * <br />
 * <a href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%BB%E5%8A%A8%E8%B0%83%E7%94%A8">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    corpid      是  企业Id
    corpsecret  是  管理组的凭证密钥
 * </pre>
 */
public class Gettoken extends WxcpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "corpid", "corpsecret"
    );

    public static final String KEY_CORPID        = "corpid";
    public static final String KEY_CORPSECRET    = "corpsecret";

    public static final String URL_API_BASE = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

  // CONSTRUCT
    public Gettoken(Properties prop)
    {
        super(prop);

        return;
    }

    public Gettoken(String corpid, String corpsecret)
    {
        super(new Properties());

        super.setProperty(KEY_CORPID    , corpid        )
            .setProperty(KEY_CORPSECRET , corpsecret    );

        return;
    }

    public Gettoken setCorpid(String corpid)
    {
        super.setProperty(KEY_CORPID, corpid);

        return(this);
    }

    public Gettoken setCorpsecret(String corpsecret)
    {
        super.setProperty(KEY_CORPSECRET, corpsecret);

        return(this);
    }


  // BUILD
    @Override
    public Gettoken build()
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
    public GettokenResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new GettokenResponse(respJson));
    }
}
