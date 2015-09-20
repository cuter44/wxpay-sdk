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
    <h1>微信分组创建样例</h1>

    <form id="form" method="GET">
      <table>
        <tr><td>分组名</td><td><input id="name" name="name" size="32" /></td></tr>
        <tr><td></td><td><input type="submit"></td></tr>
      </table>
      <script>
        (function(){
          var ran = Math.random().toString().substr(3, 4);
          document.getElementById("name").value = getParamValue("name") || ran;
        })();
      </script>
    </form>

    <p />

    <% 
      String name = request.getParameter("name");
      if (name != null)
      {
        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        GroupsCreate wxreq1 = (GroupsCreate)factory.instantiate(GroupsCreate.class);

        wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setName(name);

        GroupsCreateResponse wxresp1 = wxreq1.build().execute();
        JSONObject prop1 = wxresp1.getJson();
        System.out.println(prop1);

        out.println(prop1);
      }
    %>
  </body>
</html>
