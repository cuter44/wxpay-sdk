package com.github.cuter44.wxpay.resps;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Date;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.github.cuter44.nyafx.crypto.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxpay.constants.*;

public class Notify extends WxpayResponseBase
{
  // CONSTANTS
    //protected Boolean validity = null;
    public static final String KEY_APPID                = "appid";
    public static final String KEY_MCH_ID               = "mch_id";
    public static final String KEY_OPENID               = "openid";
    public static final String KEY_TRANSACTION_ID       = "transaction_id";
    public static final String KEY_OUT_TRADE_NO         = "out_trade_no";
    public static final String KEY_TOTAL_FEE            = "total_fee";
    public static final String KEY_CASH_FEE             = "cash_fee";
    public static final String KEY_COUPON_FEE           = "coupon_fee";
    public static final String KEY_COUPON_COUNT         = "coupon_count";

    public static List<String> KEYS_PARAM_NAME_TEMPLATE= Arrays.asList(
        "appid",
        "attach",
        "bank_type",
        "cash_fee",
        "cash_fee_type",
        "coupon_count",
        "coupon_fee",
        "coupon_fee_$0",
        "coupon_id_$0",
        "device_info",
        "err_code",
        "err_code_des",
        "fee_type",
        "is_subscribe",
        "mch_id",
        "nonce_str",
        "openid",
        "out_trade_no",
        "result_code",
        "return_code",
        "return_msg",
        "sign",
        "time_end",
        "total_fee",
        "trade_type",
        "transaction_id"
    );

    public List<String> keysParamName = null;

  // CONSTRUCT
    /** @deprecated use Notify(String notifyXml) instead.
     */
    public Notify(WxpayResponseBase resp)
    {
        this(resp.respString, resp.respProp);

        return;
    }

    /** @deprecated use Notify(String notifyXml) instead.
     */
    public Notify(String respString, Properties respProp)
    {
        super(respString, respProp);

        return;
    }

    /** @deprecated since 0.4.5.
     */
    public Notify(String notifyXml)
    {
        super(notifyXml);

        return;
    }

    public Notify(InputStream notifyXml)
        throws IOException
    {
        super(notifyXml);

        return;
    }

  //VERIFY
    protected List<String> getKeysParamName()
    {
        if (this.keysParamName != null)
            return(this.keysParamName);

        // else
        Integer couponCount = this.getCouponCount();

        List<String> l = new ArrayList<String>();
        for (String k:KEYS_PARAM_NAME_TEMPLATE)
        {
            if (k.contains("$"))
                l.addAll(materializeParamNames(k, 0, couponCount));
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
  // ID
    public final String getOpenid()
    {
        return(
            super.getProperty(KEY_OPENID)
        );
    }

    public final String getOutTradeNo()
    {
        return(
            super.getProperty(KEY_OUT_TRADE_NO)
        );
    }

    public final String getTransactionId()
    {
        return(
            super.getProperty(KEY_TRANSACTION_ID)
        );
    }

  // FEE
    public final Integer getTotalFee()
    {
        return(
            super.getIntProperty(KEY_TOTAL_FEE)
        );
    }

    public final Integer getTotalFeeFen()
    {
        return(
            this.getTotalFee()
        );
    }

    public final Double getTotalFeeYuan()
    {
        Integer fen = this.getTotalFee();
        return(
            fen!=null ? (fen.doubleValue()/100.0) : null
        );
    }

    public final Integer getCashFee()
    {
        return(
            super.getIntProperty(KEY_CASH_FEE)
        );
    }

    public final Integer getCashFeeFen()
    {
        return(
            this.getCashFee()
        );
    }

    public final Double getCashFeeYuan()
    {
        Integer fen = this.getCashFee();
        return(
            fen!=null ? (fen.doubleValue()/100.0) : null
        );
    }

    public final Integer getCouponFee()
    {
        return(
            super.getIntProperty(KEY_COUPON_FEE)
        );
    }

    public final Integer getCouponFeeFen()
    {
        return(
            this.getCouponFee()
        );
    }

    public final Double getCouponFeeYuan()
    {
        Integer fen = this.getCouponFee();
        return(
            fen!=null ? (fen.doubleValue()/100.0) : null
        );
    }

  // COUPON
    public final Integer getCouponCount()
    {
        return(
            this.getIntProperty(KEY_COUPON_COUNT)
        );
    }
}
