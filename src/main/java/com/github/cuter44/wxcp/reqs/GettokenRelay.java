package com.github.cuter44.wxcp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxcp.resps.*;

/** 从 ATDistributeCp 取得 access_token
 * 通常由 ATSatelliteCp 自行调用.
 * 如上游服务器使用 SSL, 则需于 LOAD_TRUSTS 配置相应证书, 并于 SSL_ALGORITHM 调整 SSL 版本.
 */
public class GettokenRelay extends WxcpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "corpid"
    );

    public static final String KEY_CORPID           = "corpid";
    public static final String KEY_AT_UPSTREAM_CP   = "AT_UPSTREAM_CP";

  // CONSTRUCT
    public GettokenRelay(Properties prop)
    {
        super(prop);

        return;
    }

    public GettokenRelay(String corpid, String upstreamURL)
    {
        super(new Properties());

        this.setCorpid      (corpid     );
        this.setUpstreamURL (upstreamURL);

        return;
    }

    public GettokenRelay setCorpid(String corpid)
    {
        super.setProperty(KEY_CORPID, corpid);

        return(this);
    }

    public GettokenRelay setUpstreamURL(String upstreamURL)
    {
        super.setProperty(KEY_AT_UPSTREAM_CP, upstreamURL);

        return(this);
    }

    public String getCorpid()
    {
        return(
            super.getProperty(KEY_CORPID)
        );
    }

    public String getUpstreamURL()
    {
        return(
            super.getProperty(KEY_AT_UPSTREAM_CP)
        );
    }


  // BUILD
    @Override
    public GettokenRelay build()
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
    public GettokenRelayResponse execute()
        throws IOException
    {
        StringBuilder sb = new StringBuilder(this.getUpstreamURL());
        String corpid = this.getCorpid();
        if (corpid != null)
            sb.append("?corpid=").append(corpid);

        String url = sb.toString();

        String respJson = super.executeGet(url);

        return(new GettokenRelayResponse(respJson));
    }
}
