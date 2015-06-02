<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    com.alibaba.fastjson.*,
    com.github.cuter44.wxmp.*,
    com.github.cuter44.wxmp.reqs.*,
    com.github.cuter44.wxmp.resps.*
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
      <tr><td>发给(openid)</td><td><input id="openid" name="touser" size="32" /><button onclick="javascript:getOpenId()">acquire(需要服务号)</button></td></tr>
      <tr><td>文本</td><td><input id="body" name="content" value="喵喵喵~"/></td></tr>
      <tr><td></td><td><input type="submit"></td></tr>
    </table>
    </form>

    <p />

    <% 
      String openid = request.getParameter("openid");
      String content = request.getParameter("content");
      if ((openid != null) && (content != null))
      {
        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        MessageCustomSendText wxreq1 = new MessageCustomSendText(factory.getConf());
        wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setTouser(openid);
        wxreq1.setContent(content);

        MessageCustomSendResponse wxresp1 = wxreq1.build().execute();
        JSONObject prop1 = wxresp1.getJson();
        System.out.println(prop1);

        out.println(prop1);
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

      function getOpenId(){
        if (!getParamValue("openid"))
        {
          var thisUrl = location.href;
          location.href="snsapi-base.api?redir="+encodeURIComponent(thisUrl);
        }
        else
        {
          document.getElementById("openid").value = getParamValue("openid");  
        }
      }

          
    </script>
  </body>
</html>
