package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class EventView extends EventBase
{
  // CONSTRUCT
    public EventView()
    {
        super();

        return;
    }

    public EventView(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSORS
    public static final String KEY_EVENT_KEY = "EventKey";

    public final String getEventKey()
    {
        return(
            super.getProperty(KEY_EVENT_KEY)
        );
    }
}
