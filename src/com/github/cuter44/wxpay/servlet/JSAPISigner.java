package com.github.cuter44.wxpay.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

import com.github.cuter44.nyafx.servlet.*;
import static com.github.cuter44.nyafx.servlet.Params.needString;
import static com.github.cuter44.nyafx.servlet.Params.needDouble;

import com.github.cuter44.wxpay.*;
import com.github.cuter44.wxpay.reqs.*;
import com.github.cuter44.wxpay.resps.*;
import com.github.cuter44.wxpay.constants.*;

/** Accept order info and sign, Then return json for used in getBrandWCPayRequest
 * Notice that currently this interface supported the min set of featured parameters, and handles data roughly.
 * In most case you may rewrite one to fit your situation.
 *
 * DO NOT use this servlet in a multi-instance environment,
 * due to this servlet use a static appid and secret read from the config file.
 */
public class JSAPISigner extends HttpServlet
{
    private static final String BODY        = "body";
    private static final String TOTAL_FEE   = "total_fee";
    private static final String OPENID      = "openid";

    protected WxpayFactory wxpayFactory;

    @Override
    public void init()
    {
        this.wxpayFactory = WxpayFactory.getDefaultInstance();

        return;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse  resp)
        throws IOException, ServletException
    {
        try
        {
            //req.setCharacterEncoding("utf-8");

            UnifiedOrder order = this.wxpayFactory.newUnifiedOrder()
                .setBody            (needString(req, BODY)                      )
                .setTotalFee        (needDouble(req, TOTAL_FEE)                 )
                .setOpenid          (needString(req, OPENID)                    )
                .setOutTradeNo      ("test"+Long.toString(new Date().getTime()) )
                .setSpbillCreateIp  (req.getRemoteAddr()                        )
                .setTradeType       (TradeType.JSAPI                            )
                .build()
                .sign();

            UnifiedOrderResponse orderResp = order.execute();

            System.out.println(orderResp.getProperties());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
