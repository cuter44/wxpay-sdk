package com.github.cuter44.wxmsg.reply;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

public class ReplyVoice extends WxmsgReplyBase
{
  // CONSTANT
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "ToUserName",
        "FromUserName",
        "CreateTime",
        "MsgType",
        "Voice"
    );
    public static final List<String> KEYS_PARAM_NAME_VOICE = Arrays.asList(
        "MediaId"
    );

  // CONSTRUCT
    public ReplyVoice()
    {
        super();

        super.setMsgType(MsgType.voice);

        return;
    }

    public ReplyVoice(WxmsgBase msg)
    {
        super(msg);

        super.setMsgType(MsgType.voice);

        return;
    }

  // BUILD
    public static final String KEY_VOICE ="Voice";

    @Override
    public ReplyVoice build()
    {
        super.setProperty(
            KEY_VOICE,
            super.buildXMLBody(KEYS_PARAM_NAME_VOICE, KEY_VOICE)
        );

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
    public static final String KEY_MEDIA_ID = "MediaId";

    public ReplyVoice setMediaId(String content)
    {
        super.setProperty(KEY_MEDIA_ID, content);

        return(this);
    }

}
