package com.github.cuter44.wxmsg.msg;

import java.util.Date;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import com.github.cuter44.wxmsg.constants.MsgType;
import com.github.cuter44.wxmsg.reply.WxmsgReplyBase;
//import static com.github.cuter44.wxpay.util.XMLParser.parseXML;

public class Echo extends WxmsgBase
{
  // CONSTRUCT
    public Echo()
    {
        super();

        return;
    }

    //public Echo(InputStream xml)
        //throws IOException
    //{
        //this.prop = parseXML(xml);

        //return;
    //}

    public Echo(Properties prop)
    {
        this.prop = prop;

        return;
    }

  // ACCESSOR
    //public static final String KEY_TO_USER_NAME    = "ToUserName";
    //public static final String KEY_FROM_USER_NAME  = "FromUserName";
    //public static final String KEY_CREATE_TIME     = "CreateTime";
    //public static final String KEY_MSG_TYPE        = "MsgType";

    @Override
    public String getToUserName()
        throws UnsupportedOperationException
    {
        throw(new UnsupportedOperationException("Echo comes without ToUserName"));
    }

    @Override
    public String getFromUserName()
        throws UnsupportedOperationException
    {
        throw(new UnsupportedOperationException("Echo coms without FromUserName"));
    }

    @Override
    public Date getCreateTime()
        throws UnsupportedOperationException
    {
        throw(new UnsupportedOperationException("Echo coms without CreateTime"));
    }

    @Override
    public MsgType getMsgType()
    {
        return(MsgType.ECHO);
    }

}
