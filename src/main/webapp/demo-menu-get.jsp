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
    <h1>微信自定义菜单查询样例</h1>

    <p>

    <form id="form" enctype="application/x-www-form-urlencoded" method="post">
      <input type="submit" name="action" value="query" />
    </form>

    </p>

    <% 
      String action = request.getParameter("action");

      if (action != null)
      {
        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        MenuGet wxreq1 = (MenuGet)factory.instantiateWithToken(MenuGet.class);

        wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());

        MenuGetResponse wxresp1 = wxreq1.build().execute();

    %>

      <p>
      <textarea readonly style="height:640px; width:100%;">
      <%=JSON.toJSONString(wxresp1.asJSON(), true)%>
      </textarea>
      </p>

    <%
      }
    %>
  </body>
</html>
