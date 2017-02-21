package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class EventClick extends EventBase
{
  // CONSTRUCT
    public EventClick()
    {
        super();

        return;
    }

    public EventClick(Properties prop)
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
