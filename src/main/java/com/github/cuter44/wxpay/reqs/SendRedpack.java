package com.github.cuter44.wxpay.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.github.cuter44.wxpay.resps.SendRedpackResponse;
import com.github.cuter44.wxpay.WxpayException;
import com.github.cuter44.wxpay.WxpayProtocolException;

/**
 */
public class SendRedpack extends WxpayRequestBase
{

    public static final String URL_API_BASE = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static final String KEY_WXAPPID      = "wxappid";
    public static final String KEY_TOTAL_AMOUNT = "total_amount";
    public static final String KEY_TOTAL_NUM    = "total_num";
    public static final String KEY_MCH_BILLNO   = "mch_billno";
    public static final String KEY_RE_OPENID    = "re_openid";
    public static final String KEY_CLIENT_IP    = "client_ip";
    public static final String KEY_SEND_NAME    = "send_name";
    public static final String KEY_WISHING      = "wishing";
    public static final String KEY_ACT_NAME     = "act_name";
    public static final String KEY_REMARK       = "remark";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "act_name",
        "client_ip",
        "consume_mch_id",
        "mch_billno",
        "mch_id",
        "nonce_str",
        "re_openid",
        "remark",
        "risk_info",
        "scene_id",
        "send_name",
        "sign",
        "total_amount",
        "total_num",
        "wishing",
        "wxappid"
    );

  // CONSTRUCT
    public SendRedpack(Properties prop)
    {
        super(prop);

        if (super.getProperty(KEY_WXAPPID) == null && super.getProperty(KEY_APPID) != null)
            super.setProperty(KEY_WXAPPID, super.getProperty(KEY_APPID));

        return;
    }

  // BUILD
    @Override
    public SendRedpack build()
    {
        return(this);
    }

  // SIGN
    @Override
    public SendRedpack sign() throws UnsupportedEncodingException
    {
        super.sign(KEYS_PARAM_NAME);
        return(this);
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
    public SendRedpackResponse execute()
        throws WxpayException, WxpayProtocolException, IOException
    {
        String url = URL_API_BASE;
        String body = super.buildXMLBody(KEYS_PARAM_NAME);

        InputStream respXml = super.executePostXML(url, body);

        return(new SendRedpackResponse(respXml));
    }

  // PROPERTY
    /** 订单总金额，单位为分，不能带小数点
     */
    public SendRedpack setTotalAmount(int totalFeeInCNYFen)
    {
        super.setProperty(KEY_TOTAL_AMOUNT, Integer.toString(totalFeeInCNYFen));

        return(this);
    }

    /** wrap method
     */
    public SendRedpack setTotalAmount(double totalFeeInCNYYuan)
    {
        this.setTotalAmount(
            Double.valueOf(totalFeeInCNYYuan * 100.0).intValue()
        );

        return(this);
    }

    public SendRedpack setTotalNum(int totalNum)
    {
        super.setProperty(KEY_TOTAL_NUM, Integer.toString(totalNum));

        return(this);
    }

    public SendRedpack setMchBillno(String mchBillNo)
    {
        super.setProperty(KEY_MCH_BILLNO, mchBillNo);

        return (this);
    }

    /** Given the last 10 digits and generate valid mch_billno.
     * Requires mch_id set before.
     * On mchBillNo.digits>10, it is mod 1e10L; on <10, prepended with 0s.
     */
    public SendRedpack setMchBillno10(long mchBillNo)
    {
        String s = String.format(
            "%1$s%2$tY%2$tm%2$td%3$010d",
            super.getProperty(KEY_MCH_ID),
            System.currentTimeMillis(),
            mchBillNo % 10000000000L
        );

        super.setProperty(KEY_MCH_BILLNO, s);

        return(this);
    }

    public SendRedpack setReOpenid(String reOpenid)
    {
        super.setProperty(KEY_RE_OPENID, reOpenid);

        return(this);
    }

    public SendRedpack setClientIp(String clientIp)
    {
        super.setProperty(KEY_CLIENT_IP, clientIp);

        return(this);
    }

  // NUMAN READABLE
    public SendRedpack setSendName(String sendName)
    {
        super.setProperty(KEY_SEND_NAME, sendName);

        return(this);
    }

    public SendRedpack setWishing(String wishing)
    {
        super.setProperty(KEY_WISHING, wishing);

        return(this);
    }

    public SendRedpack setActName(String actName)
    {
        super.setProperty(KEY_ACT_NAME, actName);

        return(this);
    }

    public SendRedpack setRemark(String remark)
    {
        super.setProperty(KEY_REMARK, remark);

        return(this);
    }

}
