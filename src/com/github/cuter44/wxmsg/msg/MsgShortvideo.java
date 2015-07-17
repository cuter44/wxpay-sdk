package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class MsgShortvideo extends MsgBase
{
  // CONSTRUCT
    public MsgShortvideo()
    {
        super();

        return;
    }

    public MsgShortvideo(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_MEDIA_ID = "MediaId";
    public static final String KEY_THUMB_MEDIA_ID = "ThumbMediaId";
    public static final String KEY_FORMAT = "format";

    public String getMediaId()
    {
        return(
            super.getProperty(KEY_MEDIA_ID)
        );
    }

    public String getThumbMediaId()
    {
        return(
            super.getProperty(KEY_THUMB_MEDIA_ID)
        );
    }

    public String getFromat()
    {
        return(
            super.getProperty(KEY_FORMAT)
        );
    }
}
