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
    <h1>微信退款查询样例</h1>

    ↓ 输入单号可以查看退款记录, 单号可以在微信客户端的 我↘ > 钱包 > ┇↗ > 交易消息 > (其中的某一条) > 支付信息 > 商户单号 找到.
    <br />
    当然, 只能查到对应此公众号的交易信息.

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

        RefundQuery wxreq = new RefundQuery(factory.getConf());
        wxreq.setOutTradeNo(outTradeNo);

        RefundQueryResponse wxresp = wxreq.build().sign().execute();
        assert(wxresp.verify(factory.getConf()));
        Properties prop = wxresp.getProperties();
        System.out.println(prop);

        for (Object k:prop.keySet())
        {
    %>
      <dt><%=k%>
      <dd><%=prop.get(k)%>
    <%
        }
      }
    %>

    </dl>

  </body>
</html>
