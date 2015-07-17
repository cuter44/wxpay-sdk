package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class MsgVoice extends MsgBase
{
  // CONSTRUCT
    public MsgVoice()
    {
        super();

        return;
    }

    public MsgVoice(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_MEDIA_ID = "MediaId";
    public static final String KEY_FORMAT = "format";

    public String getMediaId()
    {
        return(
            super.getProperty(KEY_MEDIA_ID)
        );
    }

    public String getFromat()
    {
        return(
            super.getProperty(KEY_FORMAT)
        );
    }
}
