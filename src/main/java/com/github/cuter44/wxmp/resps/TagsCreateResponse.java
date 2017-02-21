package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

/** 创建标签
 * <pre style="font-size:12px">
    返回说明
    id      标签id，由微信分配
    name    标签名，UTF8编码
 * </pre>
 */
public class TagsCreateResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_TAG  = "tag";
    public static final String KEY_ID   = "id";
    public static final String KEY_NAME = "name";

  // CONSTRUCT
    public TagsCreateResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public int getId()
    {
        return(
            this.json.getJSONObject(KEY_TAG).getIntValue(KEY_ID)
        );
    }

    public String getName()
    {
        return(
            this.json.getJSONObject(KEY_TAG).getString(KEY_NAME)
        );
    }
}
