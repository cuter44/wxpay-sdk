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
public class SendRedpackResponse extends WxpayResponseBase
{
    public static final String KEY_SEND_LISTID          = "send_listid";

    public static List<String> KEYS_PARAM_NAME_TEMPLATE= Arrays.asList(
        "err_code",
        "err_code_des",
        "mch_billno",
        "mch_id",
        "re_openid",
        "result_code",
        "return_code",
        "return_msg",
        "send_listid",
        "sign",
        "total_amount",
        "wxappid"
    );

    public List<String> keysParamName = null;

  //CONSTRUCT
    public SendRedpackResponse(String respXml)
    {
        super(respXml);

        return;
    }

    public SendRedpackResponse(InputStream respXml)
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
    public String getSendListid()
    {
        return(
            super.getProperty(KEY_SEND_LISTID)
        );
    }

}
