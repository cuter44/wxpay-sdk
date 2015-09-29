package com.github.cuter44.wxpay.reqs;

import com.github.cuter44.wxpay.resps.RefundQueryResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.github.cuter44.wxpay.WxpayException;
import com.github.cuter44.wxpay.WxpayProtocolException;

/**
 * Created by Mklaus on 15/4/23.
 */
public class RefundQuery extends WxpayRequestBase {
    //KEY
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/pay/refundquery";

    public static final String KEY_OUT_TRADE_NO        = "out_trade_no";
    public static final String KEY_OUT_REFUND_NO       = "out_refund_no";
    public static final String KEY_REFUND_ID           = "refund_id";
    public static final String KEY_TRANSACTION_ID      = "transaction_id";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "appid",
        "device_info",
        "mch_id",
        "nonce_str",
        "out_refund_no",
        "out_trade_no",
        "refund_id",
        "sign",
        "transaction_id"
    );

  // CONSTRUCT
    public RefundQuery(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public RefundQuery build()
    {
        return (this);
    }

  // SIGN
    @Override
    public RefundQuery sign() throws UnsupportedEncodingException
    {
        super.sign(KEYS_PARAM_NAME);
        return (this);
    }

  // EXECUTE
    @Override
    public RefundQueryResponse execute()
        throws WxpayException, WxpayProtocolException, IOException
    {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        InputStream respXml = super.executePostXML(url, body);

        return(new RefundQueryResponse(respXml));
    }

  // TO_URL
    public String toURL()
            throws UnsupportedOperationException
    {
        throw(
            new UnsupportedOperationException("This request does not execute on client side.")
        );
    }

  // PROPERTY

    /** 商户系统内部的订单号
     */
    public RefundQuery setOutTradeNo(String outTradeNo)
    {
        super.setProperty(KEY_OUT_TRADE_NO,outTradeNo);

        return (this);
    }

    /** 微信订单号
     */
    public RefundQuery setTransactionId(String transactionId)
    {
        super.setProperty(KEY_TRANSACTION_ID,transactionId);

        return (this);
    }

    /** 商户退款单号
     */
    public RefundQuery setOutRefundNo(String outRefundNo)
    {
        super.setProperty(KEY_OUT_REFUND_NO,outRefundNo);

        return (this);
    }

    /** 微信退款单号
     * refund_id、out_refund_no、out_trade_no、transaction_id 四个参数必填一个，如果同事存在优先级为：
     * refund_id>out_refund_no>transaction_id>out_trade_no
     */
    public RefundQuery setRefundId(String refundId)
    {
        super.setProperty(KEY_REFUND_ID,refundId);

        return (this);
    }

}
