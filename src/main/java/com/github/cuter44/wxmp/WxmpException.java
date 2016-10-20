package com.github.cuter44.wxmp;

public class WxmpException extends RuntimeException
{
    public int errcode;
    public String errmsg;

    public WxmpException(int errcode, String errmsg)
    {
        super(""+errcode+":"+errmsg);

        this.errcode = errcode;

        return;
    }
}
