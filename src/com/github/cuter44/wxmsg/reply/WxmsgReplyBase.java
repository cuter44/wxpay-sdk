package com.github.cuter44.wxmsg.reply;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

/**
 * @author galin<cuter44@foxmail.com>
 * @date 2014/12/25
 */
public abstract class WxmsgReplyBase
{
    public static final String KEY_TO_USER_NAME    = "ToUserName";
    public static final String KEY_FROM_USER_NAME  = "FromUserName";
    public static final String KEY_CREATE_TIME     = "CreateTime";
    public static final String KEY_MSG_TYPE        = "MsgType";

    protected Properties prop;

  // CONSTRUCT
    public WxmsgReplyBase()
    {
        this.prop = new Properties();

        this.setProperty(KEY_CREATE_TIME, Long.toString(System.currentTimeMillis()/1000L));

        return;
    }

    /** Accquire From/To from the to-reply message, and build the instance.
     */
    public WxmsgReplyBase(WxmsgBase msg)
    {
        this();

        this.setToUserName(msg.getFromUserName());
        this.setFromUserName(msg.getToUserName());

        return;
    }

  // CONFIG
    public final String getProperty(String key)
    {
        return(
            this.prop.getProperty(key)
        );
    }

    public Properties getProperties()
    {
        return(this.prop);
    }

    /**
     * chain supported
     */
    public final WxmsgReplyBase setProperty(String key, String value)
    {
        this.prop.setProperty(key, value);
        return(this);
    }

    //public WxmsgReplyBase setCDATAProperty(String key, String value)
    //{
        //this.prop.setProperty(key, "<![CDATA["+value+"]]>");
        //return(this);
    //}

    /**
     * batch setProperty
     * @param aConf a Map contains key-value pairs, where key must be String, and values must implement toString() at least.
     */
    public WxmsgReplyBase setProperties(Map aConf)
    {
        this.prop.putAll(aConf);
        return(this);
    }

  // BUILD
    /** Subclass should implements this method, which will be called before toXML()
     */
    public abstract WxmsgReplyBase build();

  // EXPORT
    /** Subclass should states concrete content type, default gives <pre>text/xml; charset=utf-8</pre>.
     */
    public String contentType()
    {
        return("text/xml; charset=utf-8");
    }

    /** Subclass should implements this method, to generate reply content, as string
     */
    public abstract String toContent();

    protected String buildXMLBody(List<String> paramNames)
    {
        return(
            this.buildXMLBody(paramNames, "xml")
        );
    }

    protected String buildXMLBody(List<String> paramNames, String rootTag)
    {
        StringBuilder xml = new StringBuilder();

        xml.append('<').append(rootTag).append('>');

        for (String k:paramNames)
        {
            String v = this.getProperty(k);
            if (v != null)
                xml.append('<').append(k).append('>')
                   .append(v)
                   .append("</").append(k).append('>');
        }

        xml.append("</").append(rootTag).append('>');

        return(xml.toString());
    }


  // MISC
    public String getToUserName()
    {
        return(
            this.getProperty(KEY_TO_USER_NAME)
        );
    }

    public WxmsgReplyBase setToUserName(String toUserName)
    {
        this.setProperty(KEY_TO_USER_NAME, toUserName);

        return(this);
    }

    public String getFromUserName()
    {
        return(
            this.getProperty(KEY_FROM_USER_NAME)
        );
    }

    public WxmsgReplyBase setFromUserName(String fromUserName)
    {
        this.setProperty(KEY_FROM_USER_NAME, fromUserName);

        return(this);
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

    public WxmsgReplyBase setCreateTime(Date createTime)
    {
        this.setProperty(
            KEY_CREATE_TIME,
            Long.toString(
                createTime.getTime()/1000L
            )
        );

        return(this);
    }

    public WxmsgReplyBase setMsgType(MsgType type)
    {
        this.setMsgType(type.toString());

        return(this);
    }

    public WxmsgReplyBase setMsgType(String type)
    {
        this.setProperty(KEY_MSG_TYPE, type);

        return(this);
    }

}

