package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 批量获取用户信息.
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html#.E6.89.B9.E9.87.8F.E8.8E.B7.E5.8F.96.E7.94.A8.E6.88.B7.E5.9F.BA.E6.9C.AC.E4.BF.A1.E6.81.AF">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    调用接口凭证
    openid_list     用户唯一标识符openid的列表（size不能超过50）
    to_groupid      分组id
 * </pre>
 * ADDITIONAL NOTE: <code>openid_list</code> is set via setOpenidList, not via setPrperty(since poor performance to parse array from Properties)
 */
public class UserInfoBatchget extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_USER_LIST    = "user_list";
    public static final String KEY_OPENID       = "openid";
    public static final String KEY_LANG         = "lang";

    public static final String VALUE_LANG_ZH_CN = "zh_CN";
    public static final String VALUE_LANG_ZH_TW = "zh_TW";
    public static final String VALUE_LANG_EN    = "en";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";

    protected JSONObject jsonBody;
    protected List<String> openidList;

  // CONSTRUCT
    public UserInfoBatchget(Properties prop)
    {
        super(prop);

        this.openidList = new ArrayList<String>();

        return;
    }

  // BUILD
    @Override
    public UserInfoBatchget build()
    {
        String lang = this.getProperty(KEY_LANG);
        lang = lang!=null ? lang : VALUE_LANG_ZH_CN;

        JSONArray userList = new JSONArray(this.openidList.size());

        for (String v:this.openidList)
        {
            JSONObject u = new JSONObject();
            u.put(KEY_OPENID, v);
            u.put(KEY_LANG, lang);
            userList.add(u);
        }

        this.jsonBody = new JSONObject();
        this.jsonBody.put(KEY_USER_LIST, userList);

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
    public UserInfoBatchgetResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new UserInfoBatchgetResponse(respJson));
    }

  // MISC
    public UserInfoBatchget setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public UserInfoBatchget setLang(String lang)
    {
        super.setProperty(KEY_LANG, lang);

        return(this);
    }

    public List<String> getUserList()
    {
        return(this.openidList);
    }

    public UserInfoBatchget setOpenidList(List<String> list)
    {
        this.openidList = list;

        return(this);
    }

    /** chain method
     */
    public UserInfoBatchget add(String openid)
    {
        this.openidList.add(openid);

        return(this);
    }

}
