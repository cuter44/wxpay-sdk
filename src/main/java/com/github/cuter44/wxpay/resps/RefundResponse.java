package com.github.cuter44.wxpay.resps;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Mklaus on 15/4/23.
 */
public class RefundResponse extends WxpayResponseBase {

    public static final String KEY_REFUND_ID            = "refund_id";
    public static final String KEY_REFUND_FEE           = "refund_fee";
    public static final String KEY_CASH_REFUND_FEE      = "cash_refund_fee";
    public static final String KEY_COUPON_REFUND_FEE    = "coupon_refund_fee";
    public static final String KEY_COUPON_REFUND_COUNT  = "coupon_refund_count";


    public static final List<String> KEYS_PARAM_NAME_TEMPLATE = Arrays.asList(
        "appid",
        "cash_fee",
        "cash_refund_fee",
        "coupon_refund_count",
        "coupon_refund_fee",
        "coupon_refund_fee_$0",
        "coupon_refund_id_$0",
        "device_info",
        "err_code",
        "err_code_des",
        "fee_type",
        "mch_id",
        "nonce_str",
        "out_refund_no",
        "out_trade_no",
        "refund_channel",
        "refund_fee",
        "refund_id",
        "result_code",
        "return_code",
        "return_msg",
        "sign",
        "total_fee",
        "transaction_id"
    );

    public List<String> keysParamName = null;

    //CONSTRUCT
    public RefundResponse(String respXml)
    {
        super(respXml);

        return;
    }

    public RefundResponse(InputStream respXml)
        throws IOException
    {
        super(respXml);

        return;
    }

    // VERIFY
    protected List<String> getKeysParamName()
    {
        if (this.keysParamName != null)
            return(this.keysParamName);

        // else
        Integer couponRefundCount = this.getCouponRefundCount();

        List<String> l = new ArrayList<String>();
        for (String k:KEYS_PARAM_NAME_TEMPLATE)
        {
            if (k.contains("$"))
                l.addAll(materializeParamNames(k, 0, couponRefundCount));
            else
                l.add(k);
        }

        this.keysParamName = l;

        return(this.keysParamName);
    }

    @Override
    protected boolean verifySign(Properties conf)
            throws UnsupportedEncodingException
    {
        return(
            super.verifySign(
                this.getKeysParamName(),
                conf
            )
        );
    }

    // PROPERTY
    public String getRefundId()
    {
        return(
            super.getProperty(KEY_REFUND_ID)
        );
    }

    /** @return refund_fee in CNY fen
     */
    public Integer getRefundFee()
    {
        return(
            super.getIntProperty(KEY_REFUND_FEE)
        );
    }

    /** @return coupon_refund_fee in CNY fen
     */
    public Integer getCashRefundFee()
    {
        return(
            super.getIntProperty(KEY_CASH_REFUND_FEE)
        );
    }

    /** @return coupon_refund_fee in CNY fen
     */
    public Integer getCouponRefundFee()
    {
        return(
            super.getIntProperty(KEY_COUPON_REFUND_FEE)
        );
    }

    public final Integer getCouponRefundCount()
    {
        return(
            super.getIntProperty(KEY_COUPON_REFUND_COUNT)
        );
    }

}
