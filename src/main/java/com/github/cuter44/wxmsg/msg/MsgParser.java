package com.github.cuter44.wxmsg.msg;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import com.github.cuter44.wxmsg.constants.MsgType;
import com.github.cuter44.wxmsg.constants.EventType;

public class MsgParser
{
    public static final String KEY_MSG_TYPE = "MsgType";
    public static final String KEY_EVENT_TYPE = "EventType";

    protected static Map<MsgType, Class<? extends WxmsgBase>> msgClassMapping;

    static {
        msgClassMapping = new HashMap<MsgType, Class<? extends WxmsgBase>>(9);

        msgClassMapping.put(MsgType.UNKNOWN     , WxmsgBase.class        );
        msgClassMapping.put(MsgType.text        , MsgText.class          );
        msgClassMapping.put(MsgType.image       , MsgImage.class         );
        msgClassMapping.put(MsgType.voice       , MsgVoice.class         );
        msgClassMapping.put(MsgType.video       , MsgVideo.class         );
        msgClassMapping.put(MsgType.shortvideo  , MsgShortvideo.class    );
        msgClassMapping.put(MsgType.location    , MsgLocation.class      );
        msgClassMapping.put(MsgType.link        , MsgLink.class          );
        msgClassMapping.put(MsgType.event       , EventBase.class        );
    }

    public static Class<? extends WxmsgBase> setMsgClass(MsgType type, Class<? extends WxmsgBase> clazz)
    {
        return(msgClassMapping.put(type, clazz));
    }

    public static Class<? extends WxmsgBase> getMsgClass(MsgType type)
    {
        return(msgClassMapping.get(type));
    }

    /** @deprecated Not recommended as String <code>type</code> is ambiguous after event type being considered.
     */
    @Deprecated
    public static Class<? extends WxmsgBase> getMsgClass(String type)
    {
        try
        {
            Class<? extends WxmsgBase> msgClass = getMsgClass(MsgType.valueOf(type));

            return(
                (msgClass != null ) ? msgClass : WxmsgBase.class
            );
        }
        catch (IllegalArgumentException ex)
        {
            return(
                getMsgClass(
                    MsgType.UNKNOWN
                )
            );
        }
        catch (NullPointerException ex)
        {
            return(
                getMsgClass(
                    MsgType.UNKNOWN
                )
            );
        }
    }

    protected static Map<EventType, Class<? extends WxmsgBase>> eventClassMapping;

    static {
        eventClassMapping = new HashMap<EventType, Class<? extends WxmsgBase>>(7);

        eventClassMapping.put(EventType.UNKNOWN     , EventBase.class           );
        eventClassMapping.put(EventType.subscribe   , EventSubscribe.class      );
        eventClassMapping.put(EventType.unsubscribe , EventUnsubscribe.class    );
        eventClassMapping.put(EventType.SCAN        , EventScan.class           );
        eventClassMapping.put(EventType.LOCATION    , EventLocation.class       );
        eventClassMapping.put(EventType.CLICK       , EventClick.class          );
        eventClassMapping.put(EventType.VIEW        , EventView.class           );
    }

    public static Class<? extends WxmsgBase> setMsgClass(EventType type, Class<? extends WxmsgBase> clazz)
    {
        return(
            eventClassMapping.put(type, clazz)
        );
    }

    public static Class<? extends WxmsgBase> getMsgClass(EventType type)
    {
        return(
            eventClassMapping.get(type)
        );
    }

    /** Type-safe method to reflect MsgType from String
     */
    public static MsgType reflectMsgType(String type)
    {
        try
        {
            return(
                MsgType.valueOf(type)
            );
        }
        catch (IllegalArgumentException ex)
        {
            return(
                MsgType.UNKNOWN
            );
        }
    }

    /** Type-safe method to reflect EventType from String
     */
    public static EventType reflectEventType(String type)
    {
        try
        {
            return(
                EventType.valueOf(type)
            );
        }
        catch (IllegalArgumentException ex)
        {
            return(
                EventType.UNKNOWN
            );
        }
    }


  // PARSE
    public static WxmsgBase parseMsg(Properties prop)
    {
        try
        {
            Class<? extends WxmsgBase> c = null;

            MsgType msgType = reflectMsgType(prop.getProperty(KEY_MSG_TYPE));

            switch (msgType)
            {
                case event:
                    EventType eventType = reflectEventType(prop.getProperty(KEY_MSG_TYPE));
                    c = getMsgClass(eventType);
                    break;
                default:
                    c = getMsgClass(msgType);
                    break;
            }

            return(
                c.getConstructor(Properties.class).newInstance(prop)
            );
        }
        catch (NoSuchMethodException ex)
        {
            throw(new RuntimeException(ex));
        }
        catch (InstantiationException ex)
        {
            throw(new RuntimeException(ex));
        }
        catch (IllegalAccessException ex)
        {
            throw(new RuntimeException(ex));
        }
        catch (java.lang.reflect.InvocationTargetException ex)
        {
            throw(new RuntimeException(ex));
        }
    }
}
