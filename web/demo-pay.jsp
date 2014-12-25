<%@ page language="java"  pageEncoding="UTF-8" %>
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
      <tr><td>商品名称</td><td><input id="body" value="喵喵喵"/></td></tr>
      <tr><td>价格</td><td><input id="total_fee" value="0.01"/></td></tr>
      <tr><td>openid</td><td><input id="openid" size="32" readonly /></td></tr>
      <tr><td></td><td><button onclick="buybuybuy()">买买买~</button></td></tr>
    </table>
    </form>
    
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
          location.href="http://weixin.uutime.cn/wxpay/get-openid.api?redir="+encodeURIComponent(thisUrl);
        }
        else
        {
          document.getElementById("openid").value = getParamValue("openid");  
        }
      })();

      function buybuybuy()
      {
        var ajax = new XMLHttpRequest();
        ajax.open(
          "POST",
          "http://weixin.uutime.cn/wxpay/jsapi-signer.api",
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
            alert(res.err_msg);
            if(res.err_msg == "get_brand_wcpay_request:ok" ) {alert("喵喵喵")}
            // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg 将在用户支付成功后返回 ok，但幵丌保证它绝对可靠。
          }
        );
      }
          
    </script>
  </body>
</html>
