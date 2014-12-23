package com.github.cuter44.wxpay.reqs;

import java.util.Properties;

import com.github.cuter44.wxpay.constants.*;

public class JSAPIUnifiedOrder extends UnifiedOrder
{
  // COSTRUCT
    public JSAPIUnifiedOrder(Properties prop)
    {
        super(prop);

        this.setTradeType(TradeType.JSAPI);

        return;
    }
}
