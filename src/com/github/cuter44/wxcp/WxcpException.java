package com.github.cuter44.wxcp;

public class WxcpException extends RuntimeException
{
    public int errcode;
    public String errmsg;

    public WxcpException(int errcode, String errmsg)
    {
        super(""+errcode+":"+errmsg);

        this.errcode = errcode;

        return;
    }
}
