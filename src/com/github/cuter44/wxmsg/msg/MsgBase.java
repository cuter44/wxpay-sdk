package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class MsgBase extends WxmsgBase
{
  // CONSTRUCT
    public MsgBase()
    {
        super();

        return;
    }

    public MsgBase(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_MSG_ID = "MsgId";

    public String getMsgId()
    {
        return(
            super.getProperty(KEY_MSG_ID)
        );
    }
}
