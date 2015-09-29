package com.github.cuter44.wxpay.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.InputStream;
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

    public static final String KEY_BODY              = "body";
    public static final String KEY_OUT_TRADE_NO      = "out_trade_no";
    public static final String KEY_TOTAL_FEE         = "total_fee";
    public static final String KEY_SPBILL_CREATE_IP  = "spbill_create_ip";
    public static final String KEY_TRADE_TYPE        = "trade_type";
    public static final String KEY_OPENID            = "openid";
    public static final String KEY_PRODUCT_ID        = "product_id";
    public static final String KEY_TIME_START        = "time_start";
    public static final String KEY_TIME_EXPIRE       = "time_expire";

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
    public UnifiedOrderResponse execute()
        throws IOException
    {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        InputStream respXml = super.executePostXML(url, body);

        return(new UnifiedOrderResponse(respXml));
    }

  // PROPERTY
    public UnifiedOrder setBody(String body)
    {
        super.setProperty(KEY_BODY, body);

        return(this);
    }

    /** 商户系统内部的订单号, 32个字符内、可包含字母
     */
    public UnifiedOrder setOutTradeNo(String outTradeNo)
    {
        super.setProperty(KEY_OUT_TRADE_NO, outTradeNo);

        return(this);
    }

    /** 订单总金额，单位为分，不能带小数点
     */
    public final UnifiedOrder setTotalFee(int totalFeeInCNYFen)
    {
        super.setProperty(KEY_TOTAL_FEE, Integer.toString(totalFeeInCNYFen));

        return(this);
    }

    /** 订单总金额，单位元
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
        super.setProperty(KEY_SPBILL_CREATE_IP, ipAddress);

        return(this);
    }

    /** 接收微信支付成功通知
     */
    public UnifiedOrder setNotifyUrl(String notifyUrl)
    {
        super.setProperty(KEY_NOTIFY_URL, notifyUrl);

        return(this);
    }

    public UnifiedOrder setTradeType(TradeType tradeType)
    {
        super.setProperty(KEY_TRADE_TYPE, tradeType.toString());

        return(this);
    }

    /** 用户在商户 appid 下的唯一标识，trade_type 为 JSAPI 时，此参数必传
     */
    public UnifiedOrder setOpenid(String openid)
    {
        super.setProperty(KEY_OPENID, openid);

        return(this);
    }

    public UnifiedOrder setProductId(Object productId)
    {
        super.setProperty(KEY_PRODUCT_ID, productId.toString());

        return(this);
    }

    public UnifiedOrder setTimeStart(Date timeStart)
    {
        super.setProperty(
            KEY_TIME_START,
            new SimpleDateFormat("yyyyMMddHHmmss").format(timeStart)
        );

        return(this);
    }

    public UnifiedOrder setTimeExpire(Date timeExpire)
    {
        super.setProperty(
            KEY_TIME_EXPIRE,
            new SimpleDateFormat("yyyyMMddHHmmss").format(timeExpire)
        );

        return(this);
    }
}
