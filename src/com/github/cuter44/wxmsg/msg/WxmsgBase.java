package com.github.cuter44.wxmsg.msg;

import java.util.Date;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import com.github.cuter44.wxmsg.constants.MsgType;
//import static com.github.cuter44.wxpay.util.XMLParser.parseXML;

public class WxmsgBase
{
  // CONSTRUCT
    protected Properties prop;

    public WxmsgBase()
    {
        return;
    }

    //public WxmsgBase(InputStream xml)
        //throws IOException
    //{
        //this.prop = parseXML(xml);

        //return;
    //}

    public WxmsgBase(Properties prop)
    {
        this.prop = prop;

        return;
    }

  // PROP
    public Properties getProperties()
    {
        return(this.prop);
    }

    public String getProperty(String key)
    {
        return(
            this.prop.getProperty(key)
        );
    }

  // ACCESSOR
    public static final String KEY_TO_USER_NAME    = "ToUserName";
    public static final String KEY_FROM_USER_NAME  = "FromUserName";
    public static final String KEY_CREATE_TIME     = "CreateTime";
    public static final String KEY_MSG_TYPE        = "MsgType";

    /**
     */
    public String getToUserName()
    {
        return(
            this.getProperty(KEY_TO_USER_NAME)
        );
    }

    /** @return openid
     */
    public String getFromUserName()
    {
        return(
            this.getProperty(KEY_FROM_USER_NAME)
        );
    }

    public Date getCreateTime()
    {
        return(
            new Date(
                Long.valueOf(
                    this.getProperty(KEY_CREATE_TIME)
                ) * 1000L
            )
        );
    }

    public MsgType getMsgType()
    {
        return(
            MsgType.valueOf(
                this.getProperty(KEY_MSG_TYPE)
            )
        );
    }

}
