package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 自定义菜单查询接口
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html">ref ↗</a>
 * <br />
 */
public class MenuGet extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/menu/get";

  // CONSTRUCT
    public MenuGet(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public MenuGet build()
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
    public MenuGetResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executeGet(url);

        return(new MenuGetResponse(respJson));
    }

  // MISC
    public MenuGet setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

}
