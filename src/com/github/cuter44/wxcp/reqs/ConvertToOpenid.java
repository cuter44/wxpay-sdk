package com.github.cuter44.wxcp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxcp.resps.*;

/** userid转换成openid接口
 * <br />
 * <a href="http://qydev.weixin.qq.com/wiki/index.php?title=Userid%E4%B8%8Eopenid%E4%BA%92%E6%8D%A2%E6%8E%A5%E5%8F%A3#userid.E8.BD.AC.E6.8D.A2.E6.88.90openid.E6.8E.A5.E5.8F.A3">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    是  调用接口凭证
    userid          是  企业号内的成员id
    agentid         否  需要发送红包的应用ID，若只是使用微信支付和企业转账，则无需该参数 * </pre>
 * <br />
 * Notice that this request composer does NOT urlencoded the <code>redirect_uri</code>
 */
public class ConvertToOpenid extends WxcpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_USERID        = "userid";
    public static final String KEY_AGENTID       = "agentid";

    public static final String URL_API_BASE = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid";

    protected JSONObject jsonBody;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'userid':{'type':'string'},"+
            "'agentid':{'type':'string'}"+
          "}"+
        "}"
    );

  // CONSTRUCT
    public ConvertToOpenid(Properties prop)
    {
        super(prop);

        return;
    }

    public ConvertToOpenid(String accessToken, String userid)
    {
        this(new Properties());

        super.setProperty(KEY_ACCESS_TOKEN  , accessToken   )
            .setProperty(KEY_USERID         , userid        );

        return;
    }

    public ConvertToOpenid(String accessToken, String userid, String agentid)
    {
        this(accessToken, userid);

        super.setProperty(KEY_AGENTID, agentid);

        return;
    }

  // PROPERTY
    public ConvertToOpenid setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public ConvertToOpenid setUserid(String userid)
    {
        super.setProperty(KEY_USERID, userid);

        return(this);
    }


  // BUILD
    @Override
    public ConvertToOpenid build()
    {
        this.jsonBody = super.buildJSONBody(BODY_SCHEMA, this.conf);

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
    public ConvertToOpenidResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = super.executePostJSON(url, this.jsonBody);

        return(new ConvertToOpenidResponse(respJson));
    }
}
