package com.github.cuter44.wxpay;

/**
 * Error return while return_code is FAIL
 */
public class WxpayProtocolException extends RuntimeException
{
    protected String returnMsg;

    public String getReturnMsg()
    {
        return(this.returnMsg);
    }

    @Override
    public String getMessage()
    {
        return(this.returnMsg);
    }

    public WxpayProtocolException(String returnMsg)
    {
        this.returnMsg = returnMsg;

        return;
    }
}
