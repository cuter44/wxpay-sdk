package com.github.cuter44.wxpay.resps;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.github.cuter44.nyafx.crypto.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxpay.constants.*;

public class Notify extends ResponseBase
{
  // CONSTANTS
    //protected Boolean validity = null;
    public static final String KEY_OPENID       = "openid";
    public static final String KEY_OUT_TRADE_NO = "out_trade_no";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "appid",
        "attach",
        "bank_type",
        "coupon_fee",
        "device_info",
        "err_code",
        "err_code_des",
        "fee_type",
        "is_subscribe",
        "mch_id",
        "nonce_str",
        "openid",
        "out_trade_no",
        "result_code",
        "return_code",
        "return_msg",
        "time_end",
        "total_fee",
        "transaction_id",
        "trade_type"
    );


  // CONSTRUCT
    public Notify(ResponseBase resp)
    {
        this(resp.respString, resp.respProp);

        return;
    }

    public Notify(String respString, Properties respProp)
    {
        super(respString, respProp);

        return;
    }

  // VERIFY
    @Override
    protected boolean verifySign(Properties conf)
        throws UnsupportedEncodingException
    {
        return(
            this.verifySign(KEYS_PARAM_NAME, conf)
        );
    }


  // GET
    public String getProperty(String key)
    {
        return(
            this.respProp.getProperty(key)
        );
    }

  // PROPERTY
    public String getOpenid()
    {
        return(
            this.getProperty(KEY_OPENID)
        );
    }

    public String getOutTradeNo()
    {
        return(
            this.getProperty(KEY_OUT_TRADE_NO)
        );
    }



}
