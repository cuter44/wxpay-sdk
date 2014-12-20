package com.github.cuter44.wxpay.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.UnsupportedEncodingException;

import com.github.cuter44.wxpay.*;
import com.github.cuter44.wxpay.resps.*;
//import com.github.cuter44.wxpay.helper.*;

public class UnifiedOrder extends RequestBase
{
  // KEYS
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "appid",
        "attach",
        "body",
        "device_info",
        "goods_tag",
        "mch_id",
        "nonce_str",
        "notify_url",
        "openid",
        "out_trade_no",
        "product_id",
        "sign",
        "spbill_create_ip",
        "time_expire",
        "time_start",
        "total_fee",
        "trade_type"
    );

  // CONSTRUCT
    public UnifiedOrder(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public UnifiedOrder build()
    {
        return(this);
    }

  // SIGN
    @Override
    public UnifiedOrder sign()
        throws UnsupportedEncodingException
    {
        this.sign(KEYS_PARAM_NAME);
        return(this);
    }

  // TO_URL
    //public String toURL()
        //throws UnsupportedEncodingException
    //{
        //String charset = this.getProperty(KEY_CHARSET);

        //return(
            //this.toSignedURL(KEYS_PARAM_NAME, charset)
        //);
    //}

  // EXECUTE
    @Override
    public ResponseBase execute()
    {
        return(
            new UnifiedOrderResponse(
                this.execute(URL_API_BASE, KEYS_PARAM_NAME)
        ));
    }

  // PROPERTY
}
