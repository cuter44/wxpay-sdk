package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.WxmpException;

public class WxmpResponseBase
{
  // CONSTANTS
    public static final String ERRCODE  = "errcode";
    public static final String ERRMSG   = "errmsg";

    public JSONObject json;

    public WxmpResponseBase(String jsonString)
        throws WxmpException
    {
        this.json = JSON.parseObject(jsonString);

        Integer errcode = this.json.getInteger(ERRCODE);

        if ((errcode != null) && !(errcode.equals(0)))
            throw(new WxmpException(errcode, this.json.getString(ERRMSG)));

        return;
    }

    /** synonym of this.json.getString(key)
     */
    public String getProperty(String key)
    {
        return(
            this.json.getString(key)
        );
    }

    /** synonym of this.json.getString(key)
     */
    public String getString(String key)
    {
        return(
            this.json.getString(key)
        );
    }
}
