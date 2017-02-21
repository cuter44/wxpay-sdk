package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 批量为用户打标签
 * <br />
 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140837">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    openid_list     粉丝列表
    tagid           标签id
 * </pre>
 * ADDITIONAL NOTE: <code>openid_list</code> is set via setOpenidList, not via setPrperty(since poor performance to parse array from Properties)
 */
public class TagsMembersBatchuntagging extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_TAGID        = "tagid";
    public static final String KEY_OPENID_LIST  = "openid_list";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";

    protected JSONObject jsonBody;
    protected List<String> openidList;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'tagid':{'type':'integer'}"+
        "} }"
    );

  // CONSTRUCT
    public TagsMembersBatchuntagging(Properties prop)
    {
        super(prop);

        this.openidList = new ArrayList<String>();

        return;
    }

  // BUILD
    @Override
    public TagsMembersBatchuntagging build()
    {
        this.jsonBody = super.buildJSONBody(BODY_SCHEMA, this.conf);

        this.jsonBody.put(KEY_OPENID_LIST, this.openidList);

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
    public TagsMembersBatchuntaggingResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new TagsMembersBatchuntaggingResponse(respJson));
    }

  // MISC
    public TagsMembersBatchuntagging setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public TagsMembersBatchuntagging setTagid(int tagid)
    {
        this.setTagid(Integer.toString(tagid));

        return(this);
    }

    public TagsMembersBatchuntagging setTagid(String tagid)
    {
        super.setProperty(KEY_TAGID, tagid);

        return(this);
    }

    public List<String> getOpenidList()
    {
        return(this.openidList);
    }

    public TagsMembersBatchuntagging setOpenidList(List<String> list)
    {
        this.openidList = list;

        return(this);
    }

    /** chain method
     */
    public TagsMembersBatchuntagging add(String openid)
    {
        this.openidList.add(openid);

        return(this);
    }

}
