package com.github.cuter44.wxmsg.msg;

import java.util.Date;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import com.github.cuter44.wxmsg.constants.MsgType;
import com.github.cuter44.wxmsg.reply.WxmsgReplyBase;
//import static com.github.cuter44.wxpay.util.XMLParser.parseXML;

public class WxmsgBase
{
  // CONSTRUCT
    protected Properties prop;

    public WxmsgBase()
    {
        this.prop = new Properties();

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

    public final Integer getIntProperty(String key)
    {
        String v = this.getProperty(key);
        return(
            (v!=null) ? Integer.valueOf(v) : null
        );
    }

    public final Double getDoubleProperty(String key)
    {
        String v = this.getProperty(key);
        return(
            (v!=null) ? Double.valueOf(v) : null
        );
    }

  // REPLY
    protected WxmsgReplyBase reply;

    public WxmsgReplyBase getReply()
    {
        return(this.reply);
    }

    public WxmsgBase setReply(WxmsgReplyBase reply)
    {
        this.reply = reply;

        return(this);
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
        try
        {
            return(
                MsgType.valueOf(
                    this.getProperty(KEY_MSG_TYPE)
                )
            );
        }
        catch (Exception ex)
        {
            return(MsgType.UNKNOWN);
        }
    }

}
