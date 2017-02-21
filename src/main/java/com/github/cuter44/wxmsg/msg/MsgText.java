package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class MsgText extends MsgBase
{
  // CONSTRUCT
    public MsgText()
    {
        super();

        return;
    }

    public MsgText(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_CONTENT = "Content";

    public String getContent()
    {
        return(
            super.getProperty(KEY_CONTENT)
        );
    }
}
