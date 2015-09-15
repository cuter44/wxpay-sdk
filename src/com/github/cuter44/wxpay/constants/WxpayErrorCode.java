package com.github.cuter44.wxpay.constants;

import java.util.Map;
import java.util.HashMap;

public enum WxpayErrorCode
{
  // ENUM
    SYSTEMERROR             (-1, "接口后台错误"),
    INVALID_TRANSACTIONID   (-2, "无效 transaction_id"),
    PARAM_ERROR             (-3, "提交参数错误"),
    ORDERPAID               (-4, "订单已支付 "),
    OUT_TRADE_NO_USED       (-5, "商户订单号重复 "),
    NOAUTH                  (-6, "商户无权限 "),
    NOTENOUGH               (-7, "余额不足 "),
    NOTSUPORTCARD           (-8, "不支持卡类型 "),
    ORDERCLOSED             (-9, "订单已关闭 "),
    BANKERROR               (-10, "银行系统异常 "),
    REFUND_FEE_INVALID      (-11, "退款金额大于支付金额 "),
    ORDERNOTEXIST           (-12, "订单不存在"),
    ERROR                   (-127, "UNKNOWN");

  // FIELDS
    private int code;
    private String msg;

    public int getCode()
    {
        return(this.code);
    }

    public String getMsg()
    {
        return(this.msg);
    }

  // INDEX/REFLECT
    private static Map<Integer, WxpayErrorCode> idxCode = new HashMap<Integer, WxpayErrorCode>();

    /** 从错误代码实例化
     */
    public static WxpayErrorCode forCode(int code)
    {
        return(idxCode.get(code));
    }

    /** 从错误名实例化
     */
    public static WxpayErrorCode forName(String name)
    {
        return(
            WxpayErrorCode.valueOf(name)
        );
    }

  // CONSTRUCT
    private WxpayErrorCode(int aCode, String aMsg)
    {
        this.code = aCode;
        this.msg = aMsg;

        return;
    }

    static {
        for (WxpayErrorCode i:WxpayErrorCode.values())
            idxCode.put(i.code, i);
    }

}