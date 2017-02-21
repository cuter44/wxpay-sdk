package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 删除标签
 * <br />
 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140837">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    tag             分组
    id              分组的id
 * </pre>
 */
public class TagsDelete extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_ID            = "id";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/tags/delete";

    protected JSONObject jsonBody;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'tag':{"+
              "'type':'object',"+
              "'schema':{"+
                "'properties':{"+
                  "'id':{'type':'integer'}"+
        "} } } } }"
    );

  // CONSTRUCT
    public TagsDelete(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public TagsDelete build()
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
    public TagsDeleteResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new TagsDeleteResponse(respJson));
    }

  // MISC
    public TagsDelete setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public TagsDelete setId(int id)
    {
        this.setId(Integer.toString(id));

        return(this);
    }

    public TagsDelete setId(String id)
    {
        super.setProperty(KEY_ID, id);

        return(this);
    }

}
