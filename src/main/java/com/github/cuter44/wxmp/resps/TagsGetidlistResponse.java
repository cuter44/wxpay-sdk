package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

/** 编辑标签
 * <pre style="font-size:12px">
    返回说明
    id      标签id，由微信分配
    name    标签名，UTF8编码
 * </pre>
 */
public class TagsGetidlistResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_TAGID_LIST  = "tagid_list";

  // CONSTRUCT
    public TagsGetidlistResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public JSONArray getTagidList()
    {
        return(
            this.json.getJSONArray(KEY_TAGID_LIST)
        );
    }

}
