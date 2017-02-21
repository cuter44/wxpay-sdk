package com.github.cuter44.wxcp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxcp.resps.*;

/** 获取用户基本信息(UnionID机制)
 * <br />
 * <a href="http://qydev.weixin.qq.com/wiki/index.php?title=OAuth%E9%AA%8C%E8%AF%81%E6%8E%A5%E5%8F%A3#.E6.A0.B9.E6.8D.AEcode.E8.8E.B7.E5.8F.96.E6.88.90.E5.91.98.E4.BF.A1.E6.81.AF">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    code            通过成员授权获取到的code，每次成员授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
 * </pre>
 */
public class Getuserinfo extends WxcpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token", "code"
    );

    protected static final String KEY_ACCESS_TOKEN  = "access_token";
    protected static final String KEY_CODE          = "code";

    public static final String URL_API_BASE = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";

  // CONSTRUCT
    public Getuserinfo(Properties prop)
    {
        super(prop);

        return;
    }

    public Getuserinfo(String accessToken, String code)
    {
        super(new Properties());

        this.setProperty(KEY_ACCESS_TOKEN   , accessToken   )
            .setProperty(KEY_CODE           , code          );

        return;
    }

  // BUILD
    @Override
    public Getuserinfo build()
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
    public GetuserinfoResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new GetuserinfoResponse(respJson));
    }
}
