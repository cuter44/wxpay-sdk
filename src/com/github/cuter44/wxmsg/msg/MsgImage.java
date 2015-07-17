package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class MsgImage extends MsgBase
{
  // CONSTRUCT
    public MsgImage()
    {
        super();

        return;
    }

    public MsgImage(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_PIC_URL = "PicUrl";
    public static final String KEY_MEDIA_ID = "MediaId";

    public String getPicUrl()
    {
        return(
            super.getProperty(KEY_PIC_URL)
        );
    }

    public String getMediaId()
    {
        return(
            super.getProperty(KEY_MEDIA_ID)
        );
    }
}
