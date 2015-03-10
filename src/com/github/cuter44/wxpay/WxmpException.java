package com.github.cuter44.wxpay;

public class WxmpException extends RuntimeException
{
    protected int errcode;
    protected String errmsg;

    public WxmpException(int errcode, String errmsg)
    {
        super(errmsg);

        this.errcode = errcode;

        return;
    }
}
