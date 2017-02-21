package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;
import com.alibaba.fastjson.*;

/** 自定义菜单创建接口
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html">ref ↗</a>
 */
public class MenuCreate extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_BUTTON        = "button";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/menu/create";

    protected JSONObject jsonBody;
    protected JSONArray button;

  // CONSTRUCT
    public MenuCreate(Properties prop)
    {
        super(prop);

        this.jsonBody = new JSONObject();
        this.button = new JSONArray();

        return;
    }

  // BUILD
    @Override
    public MenuCreate build()
    {
        this.jsonBody.put(KEY_BUTTON, this.button);

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
    public MenuCreateResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+this.toQueryString(KEYS_PARAM);
        System.out.println(this.button);
        System.out.println(this.jsonBody);
        String body = this.jsonBody.toString();

        String respJson = this.executePostJSON(url, body);

        return(new MenuCreateResponse(respJson));
    }

  // MISC
    public MenuCreate setButton(JSONArray botton)
    {
        this.button = button;
        System.out.println(button);
        System.out.println(this.button);

        return(this);
    }

    public MenuCreate setButton(String button)
    {
        this.button = JSON.parseArray(button);

        return(this);
    }
}
