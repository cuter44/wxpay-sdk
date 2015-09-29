package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;

/** 通过ticket换取二维码
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html#.E9.80.9A.E8.BF.87ticket.E6.8D.A2.E5.8F.96.E4.BA.8C.E7.BB.B4.E7.A0.81">ref ↗</a>
 * <br />
 * (No specification provided in official wiki)
 */
public class Showqrcode extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "ticket"
    );

    public static final String KEY_TICKET = "ticket";

    public static final String URL_API_BASE = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

  // CONSTRUCT
    public Showqrcode(Properties prop)
    {
        super(prop);

        return;
    }

    public Showqrcode(String ticket)
    {
        super(new Properties());

        this.setProperty(KEY_TICKET, ticket);

        return;
    }

    public Showqrcode(QrcodeCreateResponse resp)
    {
        super(new Properties());

        super.setProperty(KEY_TICKET, resp.getTicket());

        return;
    }

  // BUILD
    @Override
    public Showqrcode build()
    {
        return(this);
    }

  // TO_URL
    @Override
    public String toURL()
    {
        return(
            URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM)
        );
    }

  // EXECUTE
    @Override
    public WxmpResponseBase execute()
        throws IOException
    {
        throw(
            new UnsupportedOperationException("This request does not execute on server side.")
        );
    }

  // MISC
    public Showqrcode setTicket(String ticket)
    {
        super.setProperty(KEY_TICKET, ticket);

        return(this);
    }

}
