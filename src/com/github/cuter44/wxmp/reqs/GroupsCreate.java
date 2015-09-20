package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 创建分组
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/0/56d992c605a97245eb7e617854b169fc.html#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    name            分组名字（30个字符以内） *
 * </pre>
 */
public class GroupsCreate extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_NAME          = "name";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/groups/create";

    protected JSONObject jsonBody;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'group':{"+
              "'type':'object',"+
              "'schema':{"+
                "'properties':{"+
                  "'name':{'type':'string',}"+
        "} } } } }"
    );

  // CONSTRUCT
    public GroupsCreate(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public GroupsCreate build()
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
    public GroupsCreateResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new GroupsCreateResponse(respJson));
    }

  // MISC
    public GroupsCreate setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public GroupsCreate setName(String name)
    {
        super.setProperty(KEY_NAME, name);

        return(this);
    }

}
