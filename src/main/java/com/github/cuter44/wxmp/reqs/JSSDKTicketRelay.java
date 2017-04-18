package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;

/** 从 ATDistribute 取得 JSSDK ticket
 * 通常由 ATSatellite 自行调用.
 * 如上游服务器使用 SSL, 则需于 LOAD_TRUSTS 配置相应证书, 并于 SSL_ALGORITHM 调整 SSL 版本.
 */
public class JSSDKTicketRelay extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "appid"
    );

    public static final String KEY_APPID         = "appid";
    public static final String KEY_JT_UPSTREAM   = "JT_UPSTREAM";

  // CONSTRUCT
    public JSSDKTicketRelay(Properties prop)
    {
        super(prop);

        return;
    }

    public JSSDKTicketRelay(String appid, String upstreamURL)
    {
        super(new Properties());

        this.setAppid       (appid      );
        this.setUpstreamURL (upstreamURL);

        return;
    }

    public JSSDKTicketRelay setAppid(String appid)
    {
        super.setProperty(KEY_APPID, appid);

        return(this);
    }

    public JSSDKTicketRelay setUpstreamURL(String upstreamURL)
    {
        super.setProperty(KEY_JT_UPSTREAM, upstreamURL);

        return(this);
    }

    public String getAppid()
    {
        return(
            super.getProperty(KEY_APPID)
        );
    }

    public String getUpstreamURL()
    {
        return(
            super.getProperty(KEY_JT_UPSTREAM)
        );
    }


  // BUILD
    @Override
    public JSSDKTicketRelay build()
    {
        return(this);
    }

  // TO_URL
    @Override
    public String toURL()
    {
        throw(
            new UnsupportedOperationException("This request does not execute on client side.")
        );
    }

  // EXECUTE
    @Override
    public JSSDKTicketRelayResponse execute()
        throws IOException
    {
        StringBuilder sb = new StringBuilder(this.getUpstreamURL());
        String appid = this.getAppid();
        if (appid != null)
            sb.append("?appid=").append(appid);

        String url = sb.toString();

        String respJson = super.executeGet(url);

        return(new JSSDKTicketRelayResponse(respJson));
    }
}
