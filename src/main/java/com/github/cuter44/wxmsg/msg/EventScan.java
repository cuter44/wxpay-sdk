package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class EventScan extends EventBase
{
  // CONSTRUCT
    public EventScan()
    {
        super();

        return;
    }

    public EventScan(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSORS
    public static final String KEY_EVENT_KEY = "EventKey";
    public static final String KEY_TICKET = "Ticket";

    public final String getEventKey()
    {
        return(
            super.getProperty(KEY_EVENT_KEY)
        );
    }

    public String getTicket()
    {
        return(
            super.getProperty(KEY_TICKET)
        );
    }
}
