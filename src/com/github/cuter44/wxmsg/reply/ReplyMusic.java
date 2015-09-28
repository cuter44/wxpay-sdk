package com.github.cuter44.wxmsg.reply;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

public class ReplyMusic extends WxmsgReplyBase
{
  // CONSTANT
    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "ToUserName",
        "FromUserName",
        "CreateTime",
        "MsgType",
        "Music"
    );
    public static final List<String> KEYS_PARAM_NAME_MUSIC = Arrays.asList(
        "Title",
        "Description",
        "MusicUrl",
        "HQMusicUrl",
        "ThumbMediaId"
    );

  // CONSTRUCT
    public ReplyMusic()
    {
        super();

        super.setMsgType(MsgType.music);

        return;
    }

    public ReplyMusic(WxmsgBase msg)
    {
        super(msg);

        super.setMsgType(MsgType.music);

        return;
    }

  // BUILD
    public static final String KEY_MUSIC ="Music";

    @Override
    public ReplyMusic build()
    {
        super.setProperty(
            KEY_MUSIC,
            super.buildXMLBody(KEYS_PARAM_NAME_MUSIC, KEY_MUSIC)
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
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_MUSIC_URL = "MusicUrl";
    public static final String KEY_HQ_MUSIC_URL = "HQMusicUrl";
    public static final String KEY_THUMB_MEDIA_ID = "ThumbMediaId";

    public ReplyMusic setTitle(String title)
    {
        super.setProperty(KEY_TITLE, title);

        return(this);
    }

    public ReplyMusic setDescription(String description)
    {
        super.setProperty(KEY_DESCRIPTION, description);

        return(this);
    }

    public ReplyMusic setMusicUrl(String musicUrl)
    {
        super.setProperty(KEY_MUSIC_URL, musicUrl);

        return(this);
    }

    public ReplyMusic setHQMusicUrl(String HQMusicUrl)
    {
        super.setProperty(KEY_HQ_MUSIC_URL, HQMusicUrl);

        return(this);
    }

    public ReplyMusic setThumbMediaId(String thumbMediaId)
    {
        super.setProperty(KEY_THUMB_MEDIA_ID, thumbMediaId);

        return(this);
    }

}
