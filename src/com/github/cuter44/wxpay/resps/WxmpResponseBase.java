package com.github.cuter44.wxpay.resps;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxpay.WxmpException;

public class WxmpResponseBase
{
  // CONSTANTS
    public static final String ERRCODE  = "errcode";
    public static final String ERRMSG   = "errmsg";

    public JSONObject json;

    public WxmpResponseBase(String jsonString)
        throws WxmpException
    {
        try
        {
            this.json = JSON.parseObject(jsonString);
        }
        catch (Exception ex)
        {
            throw(new IllegalArgumentException("Malformed json input:"+jsonString));
        }

        Integer errcode = this.getErrcode();

        if ((errcode != null) && !(errcode.equals(0)))
            throw(new WxmpException(errcode, this.getErrmsg()));

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

    /** @return errcode if error occured, otherwise null.
     */
    public Integer getErrcode()
    {
        return(
            this.json.getInteger(ERRCODE)
        );
    }

    public String getErrmsg()
    {
        return(
            this.json.getString(ERRMSG)
        );
    }

}
