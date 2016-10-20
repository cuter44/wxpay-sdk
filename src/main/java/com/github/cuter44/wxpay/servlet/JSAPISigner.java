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
 *
 * <pre>
    Sign JSAPI pay request
    POST /jsapi-signer.api

    <strong>Params</strong>
    body        :string , description of goods
    total_fee   :double , fee in CNY Yuan
    openid      :string , client's openid

    <strong>Response</strong>
    Content-Type: application/json

 * </pre>
 * @deprecated Code of this class have been merged into demo-pay.jsp, this class will be completely abandoned on next major version.
 */
@Deprecated
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
            req.setCharacterEncoding("utf-8");

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

            //System.out.println(orderResp.getProperties());

            GetBrandWCPayRequest gbwxpr = this.wxpayFactory.newGetBrandWCPayRequest(orderResp.getProperties())
                .build()
                .sign();

            String jsonGbwxpr = gbwxpr.toJSON();

            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().print(jsonGbwxpr);

            return;
        }
        catch (Exception ex)
        {
            resp.reset();
            resp.setStatus(500);
            ex.printStackTrace(resp.getWriter());
        }
    }
}
