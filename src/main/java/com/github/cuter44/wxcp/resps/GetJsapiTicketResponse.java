package com.github.cuter44.wxcp.resps;

import java.io.IOException;
import java.net.MalformedURLException;

import com.alibaba.fastjson.*;

public class GetJsapiTicketResponse extends WxcpResponseBase
{
  // CONSTANTS
    public static final String KEY_TICKET       = "ticket";
    public static final String KEY_EXPIRES_IN   = "expires_in";

  // CONSTRUCT
    /** @return Creating time of this object.
     */
    public final long tmCreate = System.currentTimeMillis();

    public GetJsapiTicketResponse(String jsonString)
    {
        super(jsonString);

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

    public long getTmCreate()
    {
        return(this.tmCreate);
    }

}
