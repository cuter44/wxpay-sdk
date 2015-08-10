package com.github.cuter44.wxmsg.reply;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

public class ReplyText extends WxmsgReplyBase
{
  // CONSTANT
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "ToUserName",
        "FromUserName",
        "CreateTime",
        "MsgType",
        "Content"
    );

  // CONSTRUCT
    public ReplyText()
    {
        super();

        super.setMsgType(MsgType.text);

        return;
    }

    public ReplyText(WxmsgBase msg)
    {
        super(msg);

        super.setMsgType(MsgType.text);

        return;
    }

  // BUILD
    @Override
    public ReplyText build()
    {
        return(this);
    }

    @Override
    public String toContent()
    {
        return(
            super.buildXMLBody(KEYS_PARAM_NAME)
        );
    }

  // ACCESSOR
    public static final String KEY_CONTENT = "Content";

    public ReplyText setContent(String content)
    {
        super.setProperty(KEY_CONTENT, content);

        return(this);
    }

}
