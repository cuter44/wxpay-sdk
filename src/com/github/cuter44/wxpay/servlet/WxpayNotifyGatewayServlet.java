package com.github.cuter44.wxpay.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Properties;
import java.util.Enumeration;

import com.github.cuter44.nyafx.text.URLParser;
import static com.github.cuter44.wxpay.util.XMLParser.parseXML;

import com.github.cuter44.wxpay.resps.Notify;
import com.github.cuter44.wxpay.WxpayNotifyPublisher;
import com.github.cuter44.wxpay.WxpayNotifyListener;

public class WxpayNotifyGatewayServlet extends HttpServlet
{
    protected WxpayNotifyPublisher gateway = WxpayNotifyPublisher.getDefaultInstance();

    // FOR TEST ONLY
    // QUOTE ME ON PRODUCE ENVIRONMENT
    @Override
    public void init(ServletConfig config)
    {
        if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxpay.notifygateway.dump")))
        {
            this.gateway.addListener(
                new WxpayNotifyListener(){
                    @Override
                    public boolean handle(Notify n)
                    {
                        //ServletContext ctx = WxpayNotifyGatewayServlet.this.getServletContext();

                        //ctx.log(n.getString());
                        System.out.println(n.getString());
                        //ctx.log(n.getProperties().toString());
                        System.out.println(n.getProperties().toString());

                        //ctx.log("verify notify... "+n.verify(AlipayFactory.getDefaultInstance().getConf()));
                        //System.out.println("verify notify... "+n.verify(AlipayFactory.getDefaultInstance().getConf()));

                        return(false);
                    }
                }
            );
        }

        if (Boolean.valueOf(config.getInitParameter("com.github.cuter44.wxpay.notifygateway.dryrun")))
        {
            this.gateway.addListener(
                new WxpayNotifyListener(){
                    @Override
                    public boolean handle(Notify n)
                    {
                        return(true);
                    }
                }
            );
        }
    }

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
        Notify n = new Notify(null, parsedProp);

        System.out.println(parsedProp);

        if (gateway.publish(n))
            out.print("<xml><return_code>SUCCESS</return_code></xml>");
        // else
        out.print("<xml><return_code>FAIL</return_code><return_msg>NO_HANDLER_REPORTED</return_msg></xml>");

        return;
    }

    public void addListener(WxpayNotifyListener l)
    {
        this.gateway.addListener(l);

        return;
    }

    public void removeListener(WxpayNotifyListener l)
    {
        this.gateway.removeListener(l);

        return;
    }
}
