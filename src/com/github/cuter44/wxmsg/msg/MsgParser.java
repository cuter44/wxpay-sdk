package com.github.cuter44.wxmsg.msg;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import com.github.cuter44.wxmsg.constants.MsgType;

public class MsgParser
{
    public static final String KEY_MSG_TYPE = "MsgType";

    protected static Map<MsgType, Class<? extends WxmsgBase>> mapping;

    static
    {
        mapping = new HashMap<MsgType, Class<? extends WxmsgBase>>(16);

        mapping.put(MsgType.UNKNOWN     , WxmsgBase.class       );
        mapping.put(MsgType.text        , MsgText.class         );
        mapping.put(MsgType.image       , MsgImage.class        );
        mapping.put(MsgType.voice       , MsgVoice.class        );
        mapping.put(MsgType.video       , MsgVideo.class        );
        mapping.put(MsgType.shortvideo  , MsgShortvideo.class   );
        mapping.put(MsgType.location    , MsgLocation.class     );
        mapping.put(MsgType.link        , MsgLink.class         );
    }

    public static Class<? extends WxmsgBase> getMsgClass(MsgType type)
    {
        return(mapping.get(type));
    }

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

    public static WxmsgBase parseMsg(Properties prop)
    {
        try
        {
            Class<? extends WxmsgBase> c = getMsgClass(
                prop.getProperty(KEY_MSG_TYPE)
            );

            // STANDARD
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
