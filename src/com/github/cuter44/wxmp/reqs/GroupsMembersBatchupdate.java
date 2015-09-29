package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 批量移动用户分组
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/0/56d992c605a97245eb7e617854b169fc.html#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    openid_list     用户唯一标识符openid的列表（size不能超过50）
    to_groupid      分组id
 * </pre>
 * ADDITIONAL NOTE: <code>openid_list</code> is set via setOpenidList, not via setPrperty(since poor performance to parse array from Properties)
 */
public class GroupsMembersBatchupdate extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_TO_GROUPID    = "to_groupid";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate";

    protected JSONObject jsonBody;
    protected List<String> openidList;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'to_groupid':{'type':'string'}"+
        "} }"
    );

  // CONSTRUCT
    public GroupsMembersBatchupdate(Properties prop)
    {
        super(prop);

        this.openidList = new ArrayList<String>();

        return;
    }

  // BUILD
    @Override
    public GroupsMembersBatchupdate build()
    {
        this.jsonBody = super.buildJSONBody(BODY_SCHEMA, this.conf);

        this.jsonBody.put("openid_list", this.openidList);

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
    public GroupsMembersBatchupdateResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new GroupsMembersBatchupdateResponse(respJson));
    }

  // MISC
    public GroupsMembersBatchupdate setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public GroupsMembersBatchupdate setToGroupid(int toGroupid)
    {
        super.setProperty(KEY_TO_GROUPID, Integer.toString(toGroupid));

        return(this);
    }

    public List<String> getOpenidList()
    {
        return(this.openidList);
    }

    public GroupsMembersBatchupdate setOpenidList(List<String> list)
    {
        this.openidList = list;

        return(this);
    }

    /** chain method
     */
    public GroupsMembersBatchupdate add(String openid)
    {
        this.openidList.add(openid);

        return(this);
    }

}
