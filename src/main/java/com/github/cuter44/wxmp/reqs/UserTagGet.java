package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 获取公众号已创建的标签
 * <br />
 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140837">ref ↗</a>
 * <br />
 */
public class UserTagGet extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_TAGID        = "tagid";
    public static final String KEY_NEXT_OPENID  = "next_openid";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/user/tag/get";

    protected JSONObject jsonBody;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'tag':{"+
              "'type':'object',"+
              "'schema':{"+
                "'properties':{"+
                  "'tagid':{'type':'integer'},"+
                  "'next_openid':{'type':'string'}"+
        "} } } } }"
    );
  // CONSTRUCT
    public UserTagGet(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public UserTagGet build()
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
    public UserTagGetResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new UserTagGetResponse(respJson));
    }

  // MISC
    public UserTagGet setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public UserTagGet setTagid(String tagid)
    {
        super.setProperty(KEY_TAGID, tagid);

        return(this);
    }

    public UserTagGet setTagid(int tagid)
    {
        this.setTagid(Integer.toString(tagid));

        return(this);
    }

    public UserTagGet setNextOpenid(String nextOpenid)
    {
        super.setProperty(KEY_NEXT_OPENID, nextOpenid);

        return(this);
    }



}
