package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class EventSubscribe extends EventBase
{
  // CONSTRUCT
    public EventSubscribe()
    {
        super();

        return;
    }

    public EventSubscribe(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_EVENT_KEY = "EventKey";
    public static final String KEY_TICKET = "Ticket";

    public final String getEventKey()
    {
        return(
            super.getProperty(KEY_EVENT_KEY)
        );
    }

    public String getQrscene()
    {
        String s = this.getEventKey();

        return(
            (s!=null) ? s.substring(8) : null
        );
    }

    public String getTicket()
    {
        return(
            super.getProperty(KEY_TICKET)
        );
    }
}
