package com.github.cuter44.wxpay.resps;

import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Date;

import com.github.cuter44.wxpay.constants.*;

/**
 * @Author "cuter44"&lt;cuter44@foxmail.com&gt;
 */
public class CloseOrderResponse extends WxpayResponseBase
{
    public static final String KEY_RESULT_MSG          = "result_msg";

    public static List<String> KEYS_PARAM_NAME_TEMPLATE= Arrays.asList(
        "appid",
        "err_code",
        "err_code_des",
        "mch_id",
        "nonce_str",
        "result_code",
        "result_msg",
        "return_code",
        "return_msg",
        "sign"
    );

    public List<String> keysParamName = null;

  //CONSTRUCT
    public CloseOrderResponse(String respXml)
    {
        super(respXml);

        return;
    }

    public CloseOrderResponse(InputStream respXml)
        throws IOException
    {
        super(respXml);

        return;
    }

  // VERIFY
    @Override
    protected boolean verifySign(Properties conf)
        throws UnsupportedEncodingException
    {
        return (
            super.verifySign(
                KEYS_PARAM_NAME_TEMPLATE,
                conf
            )
        );
    }

  // PROPERTY
  // ERROR
    public String getResultMsg()
    {
        return(
            super.getProperty(KEY_RESULT_MSG)
        );
    }

}
