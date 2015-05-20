package com.github.cuter44.wxpay.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import com.github.cuter44.wxpay.*;
import com.github.cuter44.wxpay.constants.*;
import com.github.cuter44.wxpay.resps.*;
//import com.github.cuter44.wxpay.helper.*;

public class UnifiedOrder extends WxpayRequestBase
{
  // KEYS
    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    protected static final String KEY_BODY              = "body";
    protected static final String KEY_OUT_TRADE_NO      = "out_trade_no";
    protected static final String KEY_TOTAL_FEE         = "total_fee";
    protected static final String KEY_SPBILL_CREATE_IP  = "spbill_create_ip";
    protected static final String KEY_TRADE_TYPE        = "trade_type";
    protected static final String KEY_OPENID            = "openid";
    protected static final String KEY_PRODUCT_ID        = "product_id";

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
    public String toURL()
        throws UnsupportedOperationException
    {
        throw(
            new UnsupportedOperationException("This request does not execute on client side")
        );
    }

  // EXECUTE
    @Override
    public UnifiedOrderResponse execute()
        throws IOException
    {
        String url = URL_API_BASE;
        String body = this.toXml(KEYS_PARAM_NAME);

        String respXml = this.executePostXML(url, body);

        return(new UnifiedOrderResponse(respXml));
    }

  // PROPERTY
    public UnifiedOrder setBody(String body)
    {
        this.setProperty(KEY_BODY, body);

        return(this);
    }

    /** 商户系统内部的订单号,32个字符内、可包含字母
     */
    public UnifiedOrder setOutTradeNo(String outTradeNo)
    {
        this.setProperty(KEY_OUT_TRADE_NO, outTradeNo);

        return(this);
    }

    /** 订单总金额，单位为分，不能带小数点
     */
    public UnifiedOrder setTotalFee(int totalFeeInCNYFen)
    {
        this.setProperty(KEY_TOTAL_FEE, Integer.toString(totalFeeInCNYFen));

        return(this);
    }

    /** wrap method
     */
    public UnifiedOrder setTotalFee(double totalFeeInCNYYuan)
    {
        this.setTotalFee(
            Double.valueOf(totalFeeInCNYYuan*100.0).intValue()
        );

        return(this);
    }

    /** 订单生成的机器 IP
     * NOTE: client-side ip, shoulb be detected by container. use <>request.getRemoteAddr(); to
     */
    public UnifiedOrder setSpbillCreateIp(String ipAddress)
    {
        this.setProperty(KEY_SPBILL_CREATE_IP, ipAddress);

        return(this);
    }

    /** 接收微信支付成功通知
     */
    public UnifiedOrder setNotifyUrl(String notifyUrl)
    {
        this.setProperty(KEY_NOTIFY_URL, notifyUrl);

        return(this);
    }

    public UnifiedOrder setTradeType(TradeType tradeType)
    {
        this.setProperty(KEY_TRADE_TYPE, tradeType.toString());

        return(this);
    }

    /** 用户在商户 appid 下的唯一标识，trade_type 为 JSAPI 时，此参数必传
     */
    public UnifiedOrder setOpenid(String openid)
    {
        this.setProperty(KEY_OPENID, openid);

        return(this);
    }

    public UnifiedOrder setProductId(Object productId)
    {
        this.setProperty(KEY_PRODUCT_ID, productId.toString());

        return(this);
    }
}
