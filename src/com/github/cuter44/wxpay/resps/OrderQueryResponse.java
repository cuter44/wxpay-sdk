package com.github.cuter44.wxpay.resps;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.cuter44.wxpay.constants.*;

/**
 * Created by Mklaus on 15/4/21.
 */
public class OrderQueryResponse extends WxpayResponseBase {

    private static final String KEY_OPENID          = "openid";
    private static final String KEY_TRANSACTION_ID  = "trade_state";
    private static final String KEY_TRADE_STATE     = "trade_state";
    private static final String KEY_TOTAL_FEE       = "total_fee";
    private static final String KEY_COUPON_FEE      = "coupon_fee";
    private static final String KEY_TIME_END        = "time_end";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "appid",
        "attach",
        "bank_type",
        "coupon_fee",
        "device_info",
        "fee_type",
        "is_subscribe",
        "mch_id",
        "nonce_str",
        "openid",
        "out_trade_no",
        "time_end",
        "total_fee",
        "trade_state",
        "trade_type",
        "transaction_id",
        "sign"
    );

  //CONSTRUCT
    public OrderQueryResponse(String respXml)
    {
        super(respXml);

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

  //PROPERTY
    public String getTransactionId()
    {
        return(
            this.getProperty(KEY_TRANSACTION_ID)
        );
    }

    public String getOpenid()
    {
        return(
            this.getProperty(KEY_OPENID)
        );
    }

    public TradeState getTradeState()
    {
        return(
            TradeState.valueOf(
                this.getProperty(KEY_TRADE_STATE)
            )
        );
    }

    /** @return total_fee in CNY fen
     */
    public int getTotalFee(){
        return(
            Integer.valueOf(
                this.getProperty(KEY_TOTAL_FEE)
            )
        );
    }

    public int getCouponFee(){
        String sCouponFee = this.getProperty(KEY_COUPON_FEE);

        if (this.getProperty(sCouponFee!=null))
            return(
                Integer.valueOf(sCouponFee)
            );
        // else
            return(0);
    }

    public String getTimeEnd(){
        return(
            this.getProperty(KEY_TIME_END)
        );
    }
}
