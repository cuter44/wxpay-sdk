package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

import com.github.cuter44.wxmsg.constants.EventType;

public class EventBase extends WxmsgBase
{
  // CONSTRUCT
    public EventBase()
    {
        super();

        return;
    }

    public EventBase(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_EVENT_TYPE = "Event";

    public EventType getEventType()
    {
        try
        {
            return(
                EventType.valueOf(
                    this.getProperty(KEY_EVENT_TYPE)
                )
            );
        }
        catch (Exception ex)
        {
            return(EventType.UNKNOWN);
        }
    }

    /** Synonym of <code>getEventType()</code>
     */
    public EventType getEvent()
    {
        return(
            this.getEventType()
        );
    }
}
