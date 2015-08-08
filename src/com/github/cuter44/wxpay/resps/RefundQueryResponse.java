package com.github.cuter44.wxpay.resps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.github.cuter44.wxpay.constants.RefundStatus;

/**
 * Created by Mklaus on 15/4/23.
 */
public class RefundQueryResponse extends WxpayResponseBase {

    public static final String KEY_REFUND_COUNT             = "refund_count";
    public static final String KEY_REFUND_ID_$0             = "refund_id_$0";
    public static final String KEY_OUT_REFUND_NO_$0         = "out_refund_no_$0";
    public static final String KEY_REFUND_FEE_$0            = "refund_fee_$0";
    public static final String KEY_REFUND_STATUS_$0         = "refund_status_$0";
    public static final String KEY_COUPON_REFUND_COUNT_$0   = "coupon_refund_count_$0";
    public static final String KEY_COUPON_REFUND_FEE_$0     = "coupon_refund_fee_$0";

    public static List<String> KEYS_PARAM_NAME_TEMPLATE= Arrays.asList(
        "appid",
        "cash_fee",
        "coupon_refund_batch_id_$0_$1",
        "coupon_refund_count_$0",
        "coupon_refund_fee_$0",
        "coupon_refund_fee_$0_$1",
        "coupon_refund_id_$0_$1",
        "device_info",
        "err_code",
        "err_code_des",
        "fee_type",
        "mch_id",
        "nonce_str",
        "out_refund_no_$0",
        "out_trade_no",
        "refund_channel_$0",
        "refund_count",
        "refund_fee",
        "refund_fee_$0",
        "refund_id_$0",
        "refund_status_$0",
        "result_code",
        "return_code",
        "return_msg",
        "sign",
        "total_fee",
        "transaction_id"
    );

    public List<String> keysParamName = null;

  //CONSTRUCT
    public RefundQueryResponse(String respXml)
    {
        super(respXml);

        return;
    }

    public RefundQueryResponse(InputStream respXml)
        throws IOException
    {
        super(respXml);

        return;
    }

  //VERIFY
    protected List<String> getKeysParamName()
    {
        if (this.keysParamName != null)
            return(this.keysParamName);

        // else
        Integer refundCount = this.getRefundCount();
        List<Integer> couponRefundCount = Arrays.asList(this.getCouponRefundCount());

        List<String> l = new ArrayList<String>();
        for (String k:KEYS_PARAM_NAME_TEMPLATE)
        {
            if (k.contains("$"))
                l.addAll(materializeParamNames(k, 0, refundCount, couponRefundCount));
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
        return (
            super.verifySign(
                this.getKeysParamName(),
                conf
            )
        );
    }

  // PROPERTY
    // REFUND_COUND
    public final int getRefundCount()
    {
        return Integer.valueOf(
            super.getProperty(KEY_REFUND_COUNT)
        );
    }

    // REFUND_ID
    public String getRefundId(int n)
    {
        return(
            super.getProperty(
                materializeParamName(KEY_REFUND_ID_$0, n)
            )
        );
    }

    public String[] getRefundId()
    {
        int count = this.getRefundCount();
        String[] v = new String[count];

        for (int i=0; i<count; i++)
            v[i] = this.getRefundId(i);

        return(v);
    }

    // OUT_REFUND_NO
    public String getOutRefundNo(int n)
    {
        return(
            super.getProperty(
                materializeParamName(KEY_OUT_REFUND_NO_$0, n)
            )
        );
    }

    public String[] getOutRefundNo()
    {
        int count = this.getRefundCount();
        String[] v = new String[count];

        for (int i=0; i<count; i++)
            v[i] = this.getOutRefundNo(i);

        return(v);
    }

    // REFUND_STATUS
    public RefundStatus getRefundStatus(int n)
    {
        return(
            RefundStatus.valueOf(
                super.getProperty(
                    materializeParamName(KEY_REFUND_STATUS_$0, n)
                )
            )
        );
    }

    public RefundStatus[] getRefundStatus()
    {
        int count = this.getRefundCount();
        RefundStatus[] v = new RefundStatus[count];

        for (int i=0; i<count; i++)
            v[i] = this.getRefundStatus(i);

        return(v);
    }

    // REFUND_FEE
    /** @return in CNY fen
     */
    public Integer getRefundFee(int n)
    {
        return(
            super.getIntProperty(
                materializeParamName(KEY_REFUND_FEE_$0, n)
            )
        );
    }

    public Integer[] getRefundFee()
    {
        int count = this.getRefundCount();
        Integer[] v = new Integer[count];

        for (int i=0; i<count; i++)
            v[i] = this.getRefundFee(i);

        return(v);
    }

  // COUPON
    public final Integer getCouponRefundFee(int n)
    {
        return(
            super.getIntProperty(
                materializeParamName(KEY_COUPON_REFUND_FEE_$0, n)
            )
        );
    }

    public Integer[] getCouponRefundFee()
    {
        int count = this.getRefundCount();
        Integer[] v = new Integer[count];

        for (int i=0; i<count; i++)
            v[i] = this.getCouponRefundFee(i);

        return(v);
    }

    public Integer getCouponRefundCount(int n)
    {
        return(
            super.getIntProperty(
                materializeParamName(KEY_COUPON_REFUND_COUNT_$0, n)
            )
        );
    }

    public Integer[] getCouponRefundCount()
    {
        int count = this.getRefundCount();
        Integer[] v = new Integer[count];

        for (int i=0; i<count; i++)
            v[i] = this.getCouponRefundCount(i);

        return(v);
    }
}
