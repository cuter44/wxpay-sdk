package com.github.cuter44.wxpay.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.github.cuter44.wxpay.resps.CloseOrderResponse;
import com.github.cuter44.wxpay.WxpayException;
import com.github.cuter44.wxpay.WxpayProtocolException;

/** 关闭订单
 * @Author "cuter44"&lt;cuter44@foxmail.com&gt;
 */
public class CloseOrder extends WxpayRequestBase{

    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/pay/closeorder";

    public static final String KEY_OUT_TRADE_NO      = "out_trade_no";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            "appid",
            "mch_id",
            "nonce_str",
            "out_trade_no",
            "sign"
    );

    //CONSTRUCT
    public CloseOrder(Properties prop){
        super(prop);

        return;
    }

    //BUILD
    @Override
    public CloseOrder build()
    {
        return(this);
    }

    //SIGN
    @Override
    public CloseOrder sign()
        throws UnsupportedEncodingException
    {
        super.sign(KEYS_PARAM_NAME);

        return(this);
    }

    // TO_URL
    public String toURL()
        throws UnsupportedOperationException
    {
        throw(
            new UnsupportedOperationException("This request does not execute on client side.")
        );
    }

    // EXECUTE
    @Override
    public CloseOrderResponse execute()
        throws WxpayException, WxpayProtocolException, IOException
    {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        InputStream respXml = super.executePostXML(url, body);

        return(new CloseOrderResponse(respXml));
    }

    /** 商户系统内部的订单号,32个字符内、可包含字母
     */
    public CloseOrder setOutTradeNo(String outTradeNo)
    {
        super.setProperty(KEY_OUT_TRADE_NO, outTradeNo);

        return(this);
    }
}
