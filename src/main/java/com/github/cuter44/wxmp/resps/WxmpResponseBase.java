package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.WxmpException;

public class WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_ERRCODE  = "errcode";
    public static final String KEY_ERRMSG   = "errmsg";

    protected JSONObject json;

    protected WxmpResponseBase()
    {
        return;
    }

    public WxmpResponseBase(String jsonString)
        throws WxmpException
    {
        this.json = JSON.parseObject(jsonString);

        if (this.isErrorEncountered())
            throw(new WxmpException(this.getErrcode(), this.getErrmsg()));

        return;
    }

    public JSONObject getJson()
    {
        return(this.json);
    }

    /** synonym of this.json.getString(key)
     */
    public final String getProperty(String key)
    {
        return(
            this.json.getString(key)
        );
    }

    /** synonym of this.json.getString(key)
     */
    public final String getString(String key)
    {
        return(
            this.json.getString(key)
        );
    }


    public final Integer getInteger(String key)
    {
        return(
            this.json.getInteger(key)
        );
    }

    public final Long getLong(String key)
    {
        return(
            this.json.getLong(key)
        );
    }

  // ERROR
    public final boolean isErrorEncountered()
    {
        Integer errcode = this.getErrcode();

        return(
            (errcode != null) && !(errcode.equals(0))
        );
    }

    public final Integer getErrcode()
    {
        return(
            this.getInteger(KEY_ERRCODE)
        );
    }

    public final String getErrmsg()
    {
        return(
            this.getString(KEY_ERRMSG)
        );
    }
}
