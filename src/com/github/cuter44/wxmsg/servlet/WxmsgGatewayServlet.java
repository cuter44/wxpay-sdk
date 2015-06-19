package com.github.cuter44.wxpay.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;
import java.util.Enumeration;

import com.github.cuter44.nyafx.text.URLParser;
import static com.github.cuter44.wxpay.util.XMLParser.parseXML;

import com.github.cuter44.wxmsg.msg.*;

public class WxmsgGatewayServlet extends HttpServlet
{
    //protected WxpayNotifyPublisher gateway;

    ///** 接收到通知时的回调方法
     //* 默认实现是调度 WxpayNotifyPublisher.getInstance(). 进行处理
     //* 覆盖此方法可以实现自己的通知处理逻辑.
     //*/
    //public boolean handle(WxmsgBase m)
    //{
        //return(
            //this.gateway.publish(m)
        //);
    //}

    ///** 默认初始化方法, 读取并配置调试开关
     //* 覆盖此方法可以删除对 web.xml 配置的访问, 以及删除对 WxpayNotifyPublisher 的访问(报告 NullPointerException)
     //*/
    //@Override
    //public void init(ServletConfig config)
    //{
        //this.gateway = WxpayNotifyPublisher.getDefaultInstance();

        //if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxpay.notifygateway.dump")))
        //{
            //this.gateway.addListener(
                //new WxpayNotifyListener(){
                    //@Override
                    //public boolean handle(Notify n)
                    //{
                        //System.out.println(n.getString());
                        //System.out.println(n.getProperties().toString());

                        //return(false);
                    //}
                //}
            //);
        //}

        //if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxpay.notifygateway.dryrun")))
        //{
            //this.gateway.addListener(
                //new WxpayNotifyListener(){
                    //@Override
                    //public boolean handle(Notify n)
                    //{
                        //return(true);
                    //}
                //}
            //);
        //}
    //}

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        req.setCharacterEncoding("utf-8");
        InputStream reqBody = req.getInputStream();

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/xml; charset=utf-8");
        PrintWriter out = resp.getWriter();

        Properties parsedProp = parseXML(reqBody);
        WxmsgBase msg = new WxmsgBase(parsedProp);

        //if (this.handle(n))
            //out.print("<xml><return_code>SUCCESS</return_code></xml>");
        // else
        out.print("success");

        return;
    }
}
