package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

/** 创建分组
 * <pre style="font-size:12px">
    返回说明
    id      分组id，由微信分配
    name    分组名字，UTF8编码
 * </pre>
 */
public class GroupsCreateResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_ID   = "id";
    public static final String KEY_NAME = "name";

  // CONSTRUCT
    public GroupsCreateResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public int getId()
    {
        return(
            Integer.valueOf(
                super.getProperty(KEY_ID)
            )
        );
    }

    public String getName()
    {
        return(
            super.getProperty(KEY_NAME)
        );
    }
}
