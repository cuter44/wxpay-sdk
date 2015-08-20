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
      <tr><td>openid</td><td><input id="openid" name="openid" size="32" /></td></tr>
      <tr><td></td><td><button name="action" value="pay">买买买~</button></td></tr>
    </table>
    </form>

    <%
      String action = request.getParameter("action");
      if ("pay".equals(action))
      {
        request.setCharacterEncoding("utf-8");
        WxpayFactory factory = WxpayFactory.getDefaultInstance();

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

      void(function getOpenId(){
        if (!getParamValue("openid"))
        {
          var thisUrl = location.href;
          location.href="snsapi-base.api?redir="+encodeURIComponent(thisUrl);
        }
        else
        {
          document.getElementById("openid").value = getParamValue("openid");  
        }
      })();

      void(function onGbwxpr(){
        if (!getParamValue("gbwxpr"))
          return;

        //else
        document.onreadystatechange = function() {
          if (document.redayState != "complete")
            return;

          // else
          var gbwcpr = JSON.parse(getParameterValue("gbwxpr"));
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
        }
      })();


      function buybuybuy(ev)
      {
        var ajax = new XMLHttpRequest();
        ajax.open(
          "POST",
          "jsapi-signer.api",
          false
        );
        ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        ajax.send(
          "body="+document.getElementById("body").value
          +"&total_fee="+document.getElementById("total_fee").value
          +"&openid="+document.getElementById("openid").value
        );

        var gbwcpr = JSON.parse(ajax.responseText);
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
