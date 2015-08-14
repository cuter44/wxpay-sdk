package com.github.cuter44.wxpay.resps;

import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Date;

import com.github.cuter44.wxpay.constants.*;

/**
 * Created by Mklaus on 15/4/21.
 */
public class OrderQueryResponse extends WxpayResponseBase {


    public static final String KEY_OPENID          = "openid";
    public static final String KEY_TRANSACTION_ID  = "trancaction_id";
    public static final String KEY_TRADE_STATE     = "trade_state";
    public static final String KEY_TOTAL_FEE       = "total_fee";
    public static final String KEY_CASH_FEE        = "cash_fee";
    public static final String KEY_COUPON_FEE      = "coupon_fee";
    public static final String KEY_COUPON_COUNT    = "coupon_count";
    public static final String KEY_TIME_END        = "time_end";

    public static List<String> KEYS_PARAM_NAME_TEMPLATE= Arrays.asList(
        "appid",
        "attach",
        "bank_type",
        "cash_fee",
        "cash_fee_type",
        "coupon_batch_id_$0",
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
        "trade_state",
        "trade_state_desc",
        "trade_type",
        "transaction_id"
    );

    public List<String> keysParamName = null;

  //CONSTRUCT
    public OrderQueryResponse(String respXml)
    {
        super(respXml);

        return;
    }

    public OrderQueryResponse(InputStream respXml)
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
    public String getTransactionId()
    {
        return(
            super.getProperty(KEY_TRANSACTION_ID)
        );
    }

    public String getOpenid()
    {
        return(
            super.getProperty(KEY_OPENID)
        );
    }

  // STATUE
    public TradeState getTradeState()
    {
        return(
            TradeState.valueOf(
                super.getProperty(KEY_TRADE_STATE)
            )
        );
    }

  // TIME
    public Date getTimeEnd(){
        return(
            super.getDateProperty(KEY_TIME_END)
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
