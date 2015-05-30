<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.util.Properties,
    com.github.cuter44.wxpay.*,
    com.github.cuter44.wxpay.reqs.*,
    com.github.cuter44.wxpay.resps.*
  "
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>

  <body>
    <h1>微信交易退款请求样例</h1>

    ↓ 输入单号可以退款, 单号可以在微信客户端的 我↘ > 钱包 > ┇↗ > 交易消息 > (其中的某一条) > 支付信息 > 商户单号 找到.
    <br />
    自动退全额.
    <br />
    <span style="color:red;">警告: 所有关联此 appid 的交易都可以通过此页面无条件退款, 此接口等同于一个公开的后门. 请勿在生产环境保留此页面. </span>

    <p />
    
    <form id="form" enctype="application/x-www-form-urlencoded">
    <table>
      <tr><td>out_trade_no</td><td><input name="outTradeNo"/></td></tr>
      <tr><td></td><td><input type="submit"></td></tr>
    </table>
    </form>

    <p />

    <dl>

    <% 
      String outTradeNo = request.getParameter("outTradeNo");
      if (outTradeNo != null)
      {
        WxpayFactory factory = WxpayFactory.getDefaultInstance();


        OrderQuery wxreq1 = new OrderQuery(factory.getConf());
        wxreq1.setOutTradeNo(outTradeNo);

        OrderQueryResponse wxresp1 = wxreq1.build().sign().execute();
        Properties prop1 = wxresp1.getProperties();
        System.out.println(prop1);


        Refund wxreq2 = new Refund(factory.getConf(), wxresp1);

        RefundResponse wxresp2 = wxreq2.build().sign().execute();
        Properties prop2 = wxresp2.getProperties();
        System.out.println(prop2);

        for (Object k:prop2.keySet())
        {
    %>
      <dt><%=k%>
      <dd><%=prop2.get(k)%>
    <%
        }
      }
    %>

    </dl>

  </body>
</html>
