package com.github.cuter44.wxmsg.msg;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import com.github.cuter44.wxmsg.constants.MsgType;

public class MsgParser
{
    //public static final String KEY_ECHOSTR = "echostr";
    public static final String KEY_MSG_TYPE = "MsgType";

    protected static Map<MsgType, Class<? extends WxmsgBase>> mapping;

    static {
        mapping = new HashMap<MsgType, Class<? extends WxmsgBase>>(16);

        mapping.put(MsgType.UNKNOWN    , WxmsgBase.class);
        //mapping.put(MsgType.ECHO       , Echo.class);
    }

    public static Class<? extends WxmsgBase> getMsgClass(MsgType type)
    {
        return(mapping.get(type));
    }

    public static Class<? extends WxmsgBase> getMsgClass(String type)
    {
        try
        {
            return(
                getMsgClass(
                    MsgType.valueOf(type)
                )
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
            //// ECHO
            //if (prop.getProperty(KEY_ECHOSTR) != null)
                //return(new Echo(prop));

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
