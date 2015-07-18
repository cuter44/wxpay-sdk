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


    public static final String KEY_APPID                = "appid";
    public static final String KEY_COUPON_REFUND_FEE_   = "refund_fee_";
    public static final String KEY_DEVICE_INFO          = "device_info";
    public static final String KEY_MCH_ID               = "mch_id";
    public static final String KEY_NONCE_STR            = "nonce_str";
    public static final String KEY_OUT_REFUND_NO_       = "out_refund_no_";
    public static final String KEY_OUT_TRADE_NO         = "nonce_str";
    public static final String KEY_REFUND_CHANNEL_      = "refund_channel_";
    public static final String KEY_REFUND_COUNT         = "refund_count";
    public static final String KEY_REFUND_FEE_          = "refund_fee_";
    public static final String KEY_REFUND_ID_           = "refund_id_";
    public static final String KEY_REFUND_STATUS_       = "refund_status_";
    public static final String KEY_TOTAL_FEE            = "total_fee";
    public static final String KEY_TRANSACTION_ID       = "transaction_id";
    public static final String KEY_SIGN                 = "sign";

    public List<String> keysParamName = null;

    //public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
            //"appid",
            //"coupon_refund_fee_0",
            //"device_info",
            //"mch_id",
            //"nonce_str",
            //"out_refund_no_0",
            //"out_trade_no",
            //"refund_channel_0",
            //"refund_count",
            //"refund_fee_0",
            //"refund_id_0",
            //"refund_status_0",
            //"total_fee",
            //"transaction_id",
            //"sign"
    //);

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
    //public List<String> build_keys_param_name()
    //{
        //List<String> params = KEYS_PARAM_NAME;

        //for (int i = 1; i < Integer.valueOf(this.getProperty("refund_count")); i++)
        //{
            //params.add((i + 1       ), "coupon_refund_fee_"     + i);
            //params.add((i * 2 + 5   ), "out_refund_no_"         + i);
            //params.add((i * 3 + 7   ), "refund_channel_"        + i);
            //params.add((i * 4 + 9   ), "refund_fee_"            + i);
            //params.add((i * 5 + 10  ), "refund_id_"             + i);
            //params.add((i * 6 + 11  ), "refund_status_"         + i);
        //}

        //return params;
    //}

    //@Override
    protected boolean verifySign(Properties conf)
            throws UnsupportedEncodingException
    {
        if (this.keysParamName == null)
        {
            int count = this.getRefundCount();
            List<String> l = new ArrayList<String>(10+6*count);

            keysParamName.add       (KEY_APPID                  );
            for (int i=0; i<count; i++)
                keysParamName.add   (KEY_COUPON_REFUND_FEE_+i   );
            keysParamName.add       (KEY_DEVICE_INFO            );
            keysParamName.add       (KEY_MCH_ID                 );
            keysParamName.add       (KEY_NONCE_STR              );
            for (int i=0; i<count; i++)
                keysParamName.add   (KEY_OUT_REFUND_NO_+i       );
            keysParamName.add       (KEY_OUT_TRADE_NO           );
            for (int i=0; i<count; i++)
                keysParamName.add   (KEY_REFUND_CHANNEL_+i      );
            keysParamName.add       (KEY_REFUND_COUNT           );
            for (int i=0; i<count; i++)
                keysParamName.add   (KEY_REFUND_FEE_+i          );
            for (int i=0; i<count; i++)
                keysParamName.add   (KEY_REFUND_ID_+i           );
            for (int i=0; i<count; i++)
                keysParamName.add   (KEY_REFUND_STATUS_+i       );
            keysParamName.add       (KEY_TOTAL_FEE              );
            keysParamName.add       (KEY_TRANSACTION_ID         );
            keysParamName.add       (KEY_SIGN                   );

            this.keysParamName = l;
        }

        return (
            super.verifySign(this.keysParamName, conf)
        );
    }

  // PROPERTY
    public int getRefundCount()
    {
        return Integer.valueOf(
            super.getProperty(KEY_REFUND_COUNT)
        );
    }


    public String getRefundId(int i)
    {
        return(
            super.getProperty(KEY_REFUND_ID_+i)
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


    public String getOutRefundNo(int i)
    {
        return(
            super.getProperty(KEY_OUT_REFUND_NO_+i)
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


    /** @return in CNY fen
     */
    public int getRefundFee(int i)
    {
        return(
            Integer.valueOf(
                super.getProperty(KEY_REFUND_FEE_+i)
            )
        );
    }

    public int[] getRefundFee()
    {
        int count = this.getRefundCount();
        int[] v = new int[count];

        for (int i=0; i<count; i++)
            v[i] = this.getRefundFee(i);

        return(v);
    }


    public Integer getCouponRefundFee(int i)
    {
        String v = super.getProperty(KEY_COUPON_REFUND_FEE_+i);
        return(
            (v!=null) ? Integer.valueOf(v) : 0
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


    public RefundStatus getRefundStatus(int i)
    {
        return(
            RefundStatus.valueOf(
                super.getProperty(KEY_REFUND_STATUS_+i)
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

}
