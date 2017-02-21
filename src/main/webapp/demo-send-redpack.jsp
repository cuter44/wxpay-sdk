<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.util.Date,
    java.util.Properties,
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
    <h1>微信红包样例</h1>
    <p />
    <span style="color:red;">警告: 可以通过此页面近乎无限制发送红包, 此接口等同于一个公开的后门. 请勿在生产环境保留此页面. </span>
    <p />
    <form id="form" enctype="application/x-www-form-urlencoded">
    <table>
      <tr><td>发送方显示名</td>   <td><input name="send_name" value="发送方"/></td></tr>
      <tr><td>祝福语</td>         <td><input name="wishing" value="祝福语"/></td></tr>
      <tr><td>活动名称</td>       <td><input name="act_name" value="活动名称"/></td></tr>
      <tr><td>备注</td>           <td><input name="remark" value="备注"/></td></tr>

      <tr><td>金额</td>           <td><input name="total_amount" pattern="[0-9]{1,3}\.[0-9]{2}" value="1.00"/></td></tr>
      <tr><td>数量</td>           <td><input name="total_num" pattern="\d+" value="1"/></td></tr>

      <tr><td>openid</td>         <td><input id="openid" name="re_openid" size="32" /><button type="button" onclick="javascript:getOpenid(event)">acquire</button></td></tr>
      <tr><td></td>               <td><button type="submit" name="action" value="submit">提交</button></td></tr>
    </table>
    </form>

    <%
      String action = request.getParameter("action");
      if ("submit".equals(action))
      {
        request.setCharacterEncoding("utf-8");
        WxpayFactory factory = WxpayFactory.getDefaultInstance();

        SendRedpack wxreq = ((SendRedpack)factory.instantiate(SendRedpack.class))
          .setSendName      (request.getParameter("send_name")                    )
          .setWishing       (request.getParameter("wishing")                      )
          .setActName       (request.getParameter("act_name")                     )
          .setRemark        (request.getParameter("remark")                       )
          .setTotalAmount   (Double.valueOf(request.getParameter("total_amount")) )
          .setTotalNum      (Integer.valueOf(request.getParameter("total_num"))   )
          .setReOpenid      (request.getParameter("re_openid")                    )
          .setMchBillno10   (System.currentTimeMillis()                           )
          .setClientIp      (request.getLocalAddr()                               ) 
          // ↑ Be aware if you have multiple interfaces and config the outgoing differ from the income.
          .build()
          .sign();

        SendRedpackResponse wxresp = wxreq.execute();

        Properties prop = wxresp.getProperties();
        System.out.println(prop);

    %>
    <dl>
    <%
        for (Object k:prop.keySet())
        {
    %>
      <dt><%=k%>
      <dd><%=prop.get(k)%>
    <%
        }
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

    </script>
  </body>
</html>
