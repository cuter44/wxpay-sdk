package com.github.cuter44.wxmsg.reply;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

public class ReplyImage extends WxmsgReplyBase
{
  // CONSTANT
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "ToUserName",
        "FromUserName",
        "CreateTime",
        "MsgType",
        "Image"
    );
    public static final List<String> KEYS_PARAM_NAME_IMAGE = Arrays.asList(
        "MediaId"
    );

  // CONSTRUCT
    public ReplyImage()
    {
        super();

        super.setMsgType(MsgType.image);

        return;
    }

    public ReplyImage(WxmsgBase msg)
    {
        super(msg);

        super.setMsgType(MsgType.image);

        return;
    }

  // BUILD
    public static final String KEY_IMAGE ="Image";

    @Override
    public ReplyImage build()
    {
        super.setProperty(
            KEY_IMAGE,
            super.buildXMLBody(KEYS_PARAM_NAME_IMAGE, KEY_IMAGE)
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

    public ReplyImage setMediaId(String mediaId)
    {
        super.setProperty(KEY_MEDIA_ID, mediaId);

        return(this);
    }

}
