package com.github.cuter44.wxmp.resps;

import com.alibaba.fastjson.*;

/** 创建二维码ticket
 * <pre style="font-size:12px">
    返回说明
    ticket          获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
    expire_seconds  二维码的有效时间，以秒为单位。最大不超过1800。
    url             二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片 * </pre>
 */
public class QrcodeCreateResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_TICKET           = "ticket";
    public static final String KEY_EXPIRE_SECONDS   = "expire_seconds";
    public static final String KEY_URL              = "url";

  // CONSTRUCT
    public QrcodeCreateResponse(String jsonString)
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

    public int getExpireSecond()
    {
        return(
            Integer.valueOf(
                super.getProperty(KEY_EXPIRE_SECONDS)
            )
        );
    }

    public String getUrl()
    {
        return(
            super.getProperty(KEY_URL)
        );
    }
}
