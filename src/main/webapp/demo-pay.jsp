<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.util.Date,
    java.net.URLEncoder,
    com.github.cuter44.wxpay.*,
    com.github.cuter44.wxpay.reqs.*,
    com.github.cuter44.wxpay.resps.*,
    com.github.cuter44.wxpay.constants.*
  "
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>

  <body>
    <h1>微信支付样例</h1>
    ↓ 付款之后会显示喵喵喵 o("≧ω≦)o
    <p />
    <form id="form" enctype="application/x-www-form-urlencoded">
    <table>
      <tr><td>商品名称</td><td><input name="body" value="喵喵喵"/></td></tr>
      <tr><td>价格</td><td><input name="total_fee" value="0.01"/></td></tr>
      <tr><td>openid</td><td><input id="openid" name="openid" size="32" /><button type="button" onclick="javascript:getOpenid(event)">acquire</button></td></tr>
      <tr><td></td><td><button type="submit" name="action" value="sign">创建订单</button><button id="do-pay-button" type="button" onclick="javascript:buybuybuy(event);" disabled>支付</button></td></tr>
    </table>
    </form>

    <%
      String action = request.getParameter("action");
      if ("sign".equals(action))
      {
        request.setCharacterEncoding("utf-8");
        WxpayFactorySingl factory = WxpayFactorySingl.getInstance();

        UnifiedOrder order = ((UnifiedOrder)factory.instantiate(UnifiedOrder.class))
          .setBody          (request.getParameter("body")                         )
          .setTotalFee      (Double.valueOf(request.getParameter("total_fee"))    )
          .setOpenid        (request.getParameter("openid")                       )
          .setOutTradeNo    ("wxpay-demopay-"+Long.toString(new Date().getTime()) )
          .setSpbillCreateIp(request.getRemoteAddr()                              )
          .setTradeType     (TradeType.JSAPI                                      )
          .build()
          .sign();

        UnifiedOrderResponse orderResp = order.execute();

        GetBrandWCPayRequest gbwxpr = ((GetBrandWCPayRequest)factory.instantiate(
            GetBrandWCPayRequest.class,
            orderResp.getProperties()
          ))
          .build()
          .sign();

        String jsonGbwxpr = gbwxpr.toJSON();

        response.sendRedirect(
          "demo-pay.jsp"
          +"?"+
          "openid="+request.getParameter("openid")
          +"&"+
          "gbwxpr="+URLEncoder.encode(jsonGbwxpr, "utf-8")
        );

        return;
      }
    %>
    
    <script>
      function getParamValue(name)
      {
        try {
          return(
            location.search.match(new RegExp("[\?&]"+name+"=[^&#]*"))[0].split("=")[1]
          );
        } catch (ex) {
          return(null);
        }
      }

      function getOpenid(ev)
      {
        var thisUrl = location.href;
        location.href="snsapi-base.api?redir="+encodeURIComponent(thisUrl);

        ev && ev.preventDefault();
      }

      document.getElementById("openid").value = getParamValue("openid") || "";  
      document.getElementById("do-pay-button").disabled = (getParamValue("gbwxpr")==null);

      function buybuybuy(ev)
      {
        if (!getParamValue("gbwxpr"))
        {
          return;
          // to submit form
        }

        var gbwcpr = JSON.parse(decodeURIComponent(getParamValue("gbwxpr")));
        WeixinJSBridge.invoke(
          'getBrandWCPayRequest',
          gbwcpr,
          function(res){
            if(res.err_msg == "get_brand_wcpay_request:ok" )
            {
              alert("喵喵喵! ฅ(`Д´#)ฅ");
            }
            else
            {
              alert(res.err_msg);
            }
            // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg 将在用户支付成功后返回 ok，但幵丌保证它绝对可靠。
          }
        );

        ev && ev.preventDefault();
      }
          
    </script>
  </body>
</html>
