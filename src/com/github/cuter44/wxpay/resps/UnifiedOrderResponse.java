package com.github.cuter44.wxpay.resps;

import java.io.InputStream;
import java.io.IOException;

public class UnifiedOrderResponse extends WxpayResponseBase
{
    public UnifiedOrderResponse(String respXml)
    {
        super(respXml);

        return;
    }

    public UnifiedOrderResponse(InputStream respXml)
        throws IOException
    {
        super(respXml);

        return;
    }
}
