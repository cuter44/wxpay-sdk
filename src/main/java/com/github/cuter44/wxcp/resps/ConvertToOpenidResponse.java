package com.github.cuter44.wxcp.resps;

import com.alibaba.fastjson.*;

/** userid转换成openid接口
 * <br />
 * <pre style="font-size:12px">
    返回说明
    openid  企业号成员userid对应的openid，若有传参agentid，则是针对该agentid的openid。否则是针对企业号corpid的openid
    appid   应用的appid，若请求包中不包含agentid则不返回appid。该appid在使用微信红包时会用到 * </pre>
 */
public class ConvertToOpenidResponse extends WxcpResponseBase
{
  // CONSTANTS
    public static final String KEY_OPENID   = "openid";
    public static final String KEY_APPID    = "appid";

  // CONSTRUCT
    /** @return Creating time of this object.
     */
    public ConvertToOpenidResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public String getOpenid()
    {
        return(
            super.getProperty(KEY_OPENID)
        );
    }

    public String getAppid()
    {
        return(
            this.getProperty(KEY_APPID)
        );
    }
}
