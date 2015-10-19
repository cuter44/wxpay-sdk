package com.github.cuter44.wxmsg.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;
import java.util.Enumeration;

import com.github.cuter44.nyafx.text.URLParser;
import static com.github.cuter44.wxpay.util.XMLParser.parseXML;
import static com.github.cuter44.wxmsg.msg.MsgParser.parseMsg;

import com.github.cuter44.wxmsg.WxmsgDispatcher;
import com.github.cuter44.wxmsg.WxmsgHandler;
import com.github.cuter44.wxmsg.msg.*;
import com.github.cuter44.wxmsg.reply.*;
import com.github.cuter44.wxmsg.constants.*;

public class WxmsgGatewayServlet extends HttpServlet
{
    public static final String KEY_SIGNATURE    = "signature";
    public static final String KEY_TIMESTAMP    = "timestamp";
    public static final String KEY_NONCE        = "nonce";
    public static final String KEY_ECHOSTR      = "echostr";

    protected WxmsgDispatcher dispatcher;

    /** 接收到通知时的回调方法
     * 默认实现是调度 WxpayNotifyPublisher.getInstance() 进行处理
     * 覆盖此方法可以实现自己的通知处理逻辑.
     */
    public boolean handle(WxmsgBase m)
        throws Exception
    {
        return(
            this.dispatcher.handle(m)
        );
    }

    /** 默认初始化方法, 读取并配置调试开关
     * 覆盖此方法可以删除对 web.xml 配置的访问.
     */
    @Override
    public void init(ServletConfig config)
    {
        this.dispatcher = WxmsgDispatcher.getDefaultInstance();

        if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxmsg.msggateway.dump")))
        {
            this.dispatcher.subscribe(
                MsgType.POSTPROCESS,
                new WxmsgHandler()
                {
                    @Override
                    public boolean handle(WxmsgBase msg)
                    {
                        System.out.println("[WxmsgGateway dump]");
                        System.out.println("[REQ]"+msg.getProperties());

                        if (msg.getReply()!=null)
                            System.out.println("[RESP]"+msg.getReply().toContent());

                        return(false);
                    }
                }
            );
        }

        if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxmsg.msggateway.acceptalllinkin")))
        {
            this.dispatcher.subscribe(
                MsgType.ECHO,
                new WxmsgHandler()
                {
                    @Override
                    public boolean handle(WxmsgBase msg)
                    {
                        return(true);
                    }
                }
            );
        }

        if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxmsg.msggateway.echotext")))
        {
            this.dispatcher.subscribe(
                MsgType.text,
                new WxmsgHandler()
                {
                    @Override
                    public boolean handle(WxmsgBase msg)
                    {
                        MsgText m = (MsgText)msg;
                        m.setReply(
                            new ReplyText(m)
                                .setContent(m.getContent())
                        );

                        return(true);
                    }
                }
            );
        }

        if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxmsg.msggateway.replysuccess")))
        {
            this.dispatcher.subscribe(
                MsgType.FALLBACK,
                new WxmsgHandler()
                {
                    @Override
                    public boolean handle(WxmsgBase msg)
                    {
                        msg.setReply(
                            ReplySuccess.getInstance()
                        );

                        return(true);
                    }
                }
            );
        }

    }

    public void onError(Exception ex, HttpServletRequest req, HttpServletResponse resp)
        throws ServletException
    {
        ex.printStackTrace();
    }

    /** Echo
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        try
        {
            req.setCharacterEncoding("utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("text/plain; charset=utf-8");

            Properties p = new Properties();

            p.setProperty(KEY_SIGNATURE , req.getParameter(KEY_SIGNATURE)   );
            p.setProperty(KEY_TIMESTAMP , req.getParameter(KEY_TIMESTAMP)   );
            p.setProperty(KEY_NONCE     , req.getParameter(KEY_NONCE)       );
            p.setProperty(KEY_ECHOSTR   , req.getParameter(KEY_ECHOSTR)     );

            Echo e = new Echo(p);

            if (this.dispatcher.handle(e))
                resp.getWriter().write(e.getProperty(KEY_ECHOSTR));

            return;
        }
        catch (Exception ex)
        {
            this.onError(ex, req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        try
        {
            req.setCharacterEncoding("utf-8");
            InputStream reqBody = req.getInputStream();

            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();

            Properties parsedProp = parseXML(reqBody);
            WxmsgBase msg = parseMsg(parsedProp);

            if (this.handle(msg))
            {
                WxmsgReplyBase reply = msg.getReply();
                resp.setContentType(reply.contentType());
                out.println(
                    reply.toContent()
                );
            }
        }
        catch (Exception ex)
        {
            this.onError(ex, req, resp);
        }

        return;
    }
}
