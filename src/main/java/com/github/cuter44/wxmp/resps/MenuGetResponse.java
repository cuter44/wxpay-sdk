package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.menu.*;

/** 自定义菜单查询接口
 * <br />
 * (No specification provided in official wiki)
 */
public class MenuGetResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_MENU = "menu";


  // CONSTRUCT
    public MenuGetResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public String getMenu()
    {
        return(
            super.getProperty(KEY_MENU)
        );
    }

    public JSONObject asJSON()
    {
        return(
            this.json.getJSONObject(KEY_MENU)
        );
    }

    /**
     * @throws UnsupportedOperationException
     */
    public TopMenu asMenu()
    {
        throw(new UnsupportedOperationException("Not yet supported."));
    }
}
