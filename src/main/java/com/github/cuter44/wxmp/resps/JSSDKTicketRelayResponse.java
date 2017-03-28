package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

/** 获取access token
 * <br />
 * <pre style="font-size:12px">
    返回说明
    access_token    获取到的凭证
    expires_in      凭证有效时间，单位：秒
    expires         凭证失效时间，long/timestamp-ms
 * </pre>
 */
public class JSSDKTicketRelayResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_TICKET       = "ticket";
    public static final String KEY_EXPIRES_IN   = "expires_in";
    public static final String KEY_EXPIRES      = "expires";

  // CONSTRUCT
    /** @return Creating time of this object.
     */
    public final long tmCreate;

    public JSSDKTicketRelayResponse(String jsonString)
    {
        super(jsonString);

        this.tmCreate = System.currentTimeMillis();

        return;
    }

  // ACCESSOR
    public String getTicket()
    {
        return(
            super.getProperty(KEY_TICKET)
        );
    }

    /** @return expires_in is in second
     */
    public Long getExpiresIn()
    {
        return(
            this.json.getLong(KEY_EXPIRES_IN)
        );
    }

    public Long getExpires()
    {
        return(
            this.json.getLong(KEY_EXPIRES)
        );
    }

    public long getTmCreate()
    {
        return(this.tmCreate);
    }
}
