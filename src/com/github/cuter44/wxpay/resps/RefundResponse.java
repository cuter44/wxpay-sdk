package com.github.cuter44.wxpay.resps;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Mklaus on 15/4/23.
 */
public class RefundResponse extends ResponseBase {

    public static final String KEY_REFUND_ID            = "refund_id";
    public static final String KEY_REFUND_FEE           = "refund_fee";
    public static final String KEY_COUPON_REFUND_FEE    = "coupon_refund_fee";


    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "appid",
        "coupon_refund_fee",
        "device_info",
        "mch_id",
        "nonce_str",
        "out_refund_no",
        "out_trade_no",
        "refund_channel",
        "refund_fee",
        "refund_id",
        "transaction_id",
        "sign"
    );

    //CONSTRUCT
    public RefundResponse(ResponseBase resp){
        super(resp.respString,resp.respProp);

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
    public String getRefundId()
    {
        return(
            this.getProperty(KEY_REFUND_ID)
        );
    }

    /** @return refund_fee in CNY fen
     */
    public int getRefundFee(){
        return(
            Integer.valueOf(
                this.getProperty(KEY_REFUND_FEE)
            )
        );
    }

    /** @return coupon_refund_fee in CNY fen
     */
    public int getCouponRefundFee(){
        String sCouponRefundFee = this.getProperty(KEY_COUPON_FEE);

        if (this.getProperty(sCouponRefundFee!=null))
            return(
                Integer.valueOf(sCouponRefundFee)
            );
        // else
            return(0);
    }
}
