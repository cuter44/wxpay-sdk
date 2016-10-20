package com.github.cuter44.wxmsg.reply;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

/** Reply <kbd>success</kbd> to wx-server
 */
public class ReplySuccess extends WxmsgReplyBase
{
  // CONSTRUCT
    public ReplySuccess()
    {
        super();

        return;
    }

    public ReplySuccess(WxmsgBase msg)
    {
        super(msg);

        return;
    }

  // SINGLETON
    private static final ReplySuccess SINGLETON = new ReplySuccess();

    public static ReplySuccess getInstance()
    {
        return(ReplySuccess.SINGLETON);
    }

  // BUILD
    @Override
    public ReplySuccess build()
    {
        return(this);
    }

    @Override
    public String contentType()
    {
        return("text/plain; charset=utf-8");
    }

    @Override
    public String toContent()
    {
        return("success");
    }
}
