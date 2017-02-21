package com.github.cuter44.wxmsg.reply;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

public class ReplyVideo extends WxmsgReplyBase
{
  // CONSTANT
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "ToUserName",
        "FromUserName",
        "CreateTime",
        "MsgType",
        "Video"
    );
    public static final List<String> KEYS_PARAM_NAME_VIDEO = Arrays.asList(
        "MediaId",
        "Title",
        "Description"
    );

  // CONSTRUCT
    public ReplyVideo()
    {
        super();

        super.setMsgType(MsgType.voice);

        return;
    }

    public ReplyVideo(WxmsgBase msg)
    {
        super(msg);

        super.setMsgType(MsgType.voice);

        return;
    }

  // BUILD
    public static final String KEY_VIDEO ="Video";

    @Override
    public ReplyVideo build()
    {
        super.setProperty(
            KEY_VIDEO,
            super.buildXMLBody(KEYS_PARAM_NAME_VIDEO, KEY_VIDEO)
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
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DESCRIPTION = "Description";

    public ReplyVideo setMediaId(String mediaId)
    {
        super.setProperty(KEY_MEDIA_ID, mediaId);

        return(this);
    }

    public ReplyVideo setTitle(String title)
    {
        super.setProperty(KEY_TITLE, title);

        return(this);
    }

    public ReplyVideo setDescription(String description)
    {
        super.setProperty(KEY_DESCRIPTION, description);

        return(this);
    }

}
