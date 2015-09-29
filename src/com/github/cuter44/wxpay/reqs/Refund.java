package com.github.cuter44.wxpay.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.github.cuter44.wxpay.resps.RefundResponse;
import com.github.cuter44.wxpay.resps.OrderQueryResponse;
import com.github.cuter44.wxpay.WxpayException;
import com.github.cuter44.wxpay.WxpayProtocolException;
/**
 * Created by Mklaus on 15/4/23.
 */
public class Refund extends WxpayRequestBase {

    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static final String KEY_MCH_ID            = "mch_id";

    public static final String KEY_OUT_TRADE_NO      = "out_trade_no";
    public static final String KEY_OUT_REFUND_NO     = "out_refund_no";
    public static final String KEY_TRANSACTION_ID    = "transaction_id";
    public static final String KEY_TOTAL_FEE         = "total_fee";
    public static final String KEY_REFUND_FEE        = "refund_fee";
    public static final String KEY_OP_USER_ID        = "op_user_id";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "appid",
        "device_info",
        "mch_id",
        "nonce_str",
        "op_user_id",
        "out_refund_no",
        "out_trade_no",
        "refund_fee",
        "refund_fee_type",
        "sign",
        "total_fee",
        "transaction_id"
    );

  // CONSTRUCT
    public Refund(Properties prop){
        super(prop);

        if (super.getProperty(KEY_OP_USER_ID) == null)
            super.setProperty(KEY_OP_USER_ID, this.getProperty(KEY_MCH_ID));

        return;
    }

    /** Fully refund a queried order.
     * <br />
     * <code>out_trade_no</code> is read from <code>${order.out_trade_no}</code> if not provided in <code>prop<code>.
     * <br />
     * <code>out_refund_no</code> is set to <code>${order.out_trade_no}-refund</code> if not provided in <code>prop<code>.
     * Be cautious not to violate unique/length restrictions.
     * <br />
     * <code>transaction_id</code> is read from <code>${order.transaction_id}</code> if not provided in <code>prop<code>.
     * <br />
     * <code>total_fee</code> is read from <code>${order.total_fee}</code> if not provided in <code>prop</code>.
     * <br />
     * <code>refund_fee</code> is read from <code>${order.total_fee}</code> if not provided in <code>prop</code>.
     * <br />
     * Besides those, all other params provided by <code>order</code> are ignored.
     */
    public Refund(Properties prop, OrderQueryResponse order)
    {
        this(prop);

        //Properties p2 = order.getProperties();

        if (super.getProperty(KEY_OUT_TRADE_NO) == null)
            super.setProperty(KEY_OUT_TRADE_NO, order.getProperty(KEY_OUT_TRADE_NO));

        if (super.getProperty(KEY_OUT_REFUND_NO) == null)
            super.setProperty(KEY_OUT_REFUND_NO, order.getProperty(KEY_OUT_TRADE_NO)+"-refund");

        if (super.getProperty(KEY_TRANSACTION_ID) == null)
            super.setProperty(KEY_TRANSACTION_ID, order.getProperty(KEY_TRANSACTION_ID));

        if (super.getProperty(KEY_TOTAL_FEE) == null)
            super.setProperty(KEY_TOTAL_FEE, order.getProperty(KEY_TOTAL_FEE));

        if (super.getProperty(KEY_REFUND_FEE) == null)
            super.setProperty(KEY_REFUND_FEE, order.getProperty(KEY_TOTAL_FEE));

        return;
    }

  // BUILD
    @Override
    public Refund build()
    {
        return (this);
    }

  // SIGN
    @Override
    public Refund sign() throws UnsupportedEncodingException
    {
        super.sign(KEYS_PARAM_NAME);
        return (this);
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
    public RefundResponse execute()
        throws WxpayException, WxpayProtocolException, IOException
    {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        InputStream respXml = super.executePostXML(url, body);

        return(new RefundResponse(respXml));
    }

  // PROPERTY
    /** 商户系统内部的订单号,32个字符内、可包含字母
     */
    public Refund setOutTradeNo(String outTradeNo)
    {
        super.setProperty(KEY_OUT_TRADE_NO, outTradeNo);

        return(this);
    }

    /** 微信的订单号，优先使用
     */
    public Refund setTransactionId(String transactionId)
    {
        super.setProperty(KEY_TRANSACTION_ID,transactionId);

        return (this);
    }

    /** 商户系统内部的退单号,32个字符内、可包含字母
     */
    public Refund setOutRefundNo(String outRefundNo)
    {
        super.setProperty(KEY_OUT_REFUND_NO,outRefundNo);

        return (this);
    }

    /** 订单总金额，单位为分，不能带小数点
     */
    public Refund setTotalFee(int totalFeeInCNYFen)
    {
        super.setProperty(KEY_TOTAL_FEE, Integer.toString(totalFeeInCNYFen));

        return(this);
    }

    /** wrap method
     */
    public Refund setTotalFee(double totalFeeInCNYYuan)
    {
        this.setTotalFee(
            Double.valueOf(totalFeeInCNYYuan * 100.0).intValue()
        );

        return(this);
    }

    /** 退款总金额,单位为分,可以做部分退款
     */
    public Refund setRefundFee(int refundFeeInCNYFen)
    {
        super.setProperty(KEY_REFUND_FEE,Integer.toString(refundFeeInCNYFen));

        return (this);
    }

    /** wrap method
     */
    public Refund setRefundFee(double totalFeeInCNYYuan)
    {
        this.setRefundFee(
            Double.valueOf(totalFeeInCNYYuan * 100.0).intValue()
        );

        return(this);
    }

    /** 操作员帐号, 默认为商户号
     */
    public Refund setOpUserId(String opUserId)
    {
        super.setProperty(KEY_OP_USER_ID, opUserId);

        return (this);
    }
}
