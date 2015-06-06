package com.github.cuter44.wxpay.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Date;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxpay.*;
import com.github.cuter44.wxpay.constants.*;
import com.github.cuter44.wxpay.resps.*;
//import com.github.cuter44.wxpay.helper.*;

public class GetBrandWCPayRequest extends WxpayRequestBase
{
    public static final String KEY_GBWCPR_APP_ID    = "appId";
    public static final String KEY_GBWCPR_NONCE_STR = "nonceStr";
    public static final String KEY_PAY_SIGN         = "paySign";
    public static final String KEY_TIME_STAMP       = "timeStamp";
    public static final String KEY_SIGN_TYPE        = "signType";
    public static final String KEY_PREPAY_ID        = "prepay_id";
    public static final String KEY_PACKAGE          = "package";

  // KEYS
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "appId",
        "nonceStr",
        "package",
        "signType",
        "timeStamp"
    );

  // CONSTRUCT
    public GetBrandWCPayRequest(Properties prop)
    {
        super(prop);

        if (super.getProperty(KEY_GBWCPR_APP_ID) == null)
            this.setAppId(
                super.getProperty(KEY_APPID)
            );

        if (super.getProperty(KEY_GBWCPR_NONCE_STR) == null)
            this.setNonceStr(
                super.getProperty(KEY_NONCE_STR)
            );

        if (super.getProperty(KEY_PACKAGE) == null)
            this.setPackage(
                super.getProperty(KEY_PREPAY_ID)
            );

        this.setTimeStamp(new Date());
        this.setSignType("MD5");

        return;
    }

  // BUILD
    @Override
    public GetBrandWCPayRequest build()
    {
        return(this);
    }

  // SIGN
    @Override
    public GetBrandWCPayRequest sign()
        throws UnsupportedEncodingException
    {
        super.sign(KEYS_PARAM_NAME);

        super.setProperty(
            KEY_PAY_SIGN,
            super.getProperty(KEY_SIGN)
        );

        return(this);
    }

  // TO_URL
    /** NOTE This method return javascript source, NOT URL
     */
    @Override
    public String toURL()
        throws UnsupportedOperationException
    {
        return(this.toJSON());
    }

    public String toJSON()
    {
        JSONObject json = new JSONObject();

        for (String key:KEYS_PARAM_NAME)
            json.put(
                key,
                super.getProperty(key)
            );

        json.put(KEY_PAY_SIGN, super.getProperty(KEY_PAY_SIGN));

        return(json.toString());
    }

  // EXECUTE
    @Override
    public WxpayResponseBase execute()
    {
        throw(
            new UnsupportedOperationException("This request does not execute on server side")
        );
    }

  // PROPERTY
    /** 商户注册具有支付权限的公众号成功后即可获得
     */
    public GetBrandWCPayRequest setAppId(String appid)
    {
        super.setProperty(KEY_GBWCPR_APP_ID, appid);

        return(this);
    }

    /** 商户生成的随机字符串
     * Overrided method for key changing
     */
    @Override
    public WxpayRequestBase setNonceStr(String nonceStr)
    {
        super.setProperty(KEY_GBWCPR_NONCE_STR, nonceStr);

        return(this);
    }

    /** 商户生成，从 1970 年 1 月 1 日 00：00：00 至今的秒数
     */
    public WxpayRequestBase setTimeStamp(Date timeStamp)
    {
        super.setProperty(
            KEY_TIME_STAMP,
            Long.toString(timeStamp.getTime()/1000L)
        );

        return(this);
    }

    /** 必须是"MD5" o(*≧▽≦)ツ
     * Overrided method for key changing
     */
    public WxpayRequestBase setSignType(String signType)
    {
        super.setProperty(KEY_SIGN_TYPE, signType);

        return(this);
    }

    /** 统一支付接口返回的 prepay_id 参数值
     * @param paxkage prepay_id, part after the equal
     */
    public WxpayRequestBase setPackage(String paxkage)
    {
        super.setProperty(KEY_PACKAGE, "prepay_id="+paxkage);

        return(this);
    }


}
