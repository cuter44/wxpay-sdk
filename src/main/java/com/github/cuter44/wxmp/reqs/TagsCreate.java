package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 创建标签
 * <br />
 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140837">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    name            标签名（30个字符以内）
 * </pre>
 */
public class TagsCreate extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_NAME          = "name";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/tags/create";

    protected JSONObject jsonBody;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'tag':{"+
              "'type':'object',"+
              "'schema':{"+
                "'properties':{"+
                  "'name':{'type':'string'}"+
        "} } } } }"
    );

  // CONSTRUCT
    public TagsCreate(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public TagsCreate build()
    {
        this.jsonBody = super.buildJSONBody(BODY_SCHEMA, this.conf);

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
    public TagsCreateResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new TagsCreateResponse(respJson));
    }

  // MISC
    public TagsCreate setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public TagsCreate setName(String name)
    {
        super.setProperty(KEY_NAME, name);

        return(this);
    }

}
