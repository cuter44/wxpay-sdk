package com.github.cuter44.wxmsg.msg;

import java.util.Properties;
import java.awt.Point;

public class MsgLink extends MsgBase
{
  // CONSTRUCT
    public MsgLink()
    {
        super();

        return;
    }

    public MsgLink(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_URL = "Url";

    public String getTitle()
    {
        return(
            super.getProperty(KEY_TITLE)
        );
    }

    public String getDescription()
    {
        return(
            super.getProperty(KEY_DESCRIPTION)
        );
    }

    public String getUrl()
    {
        return(
            super.getProperty(KEY_URL)
        );
    }
}
