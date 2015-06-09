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
    <h1>微信分组查询样例</h1>

    <form id="form" enctype="application/x-www-form-urlencoded" method="post">
      <input type="submit" name="action" value="query" />
    </form>

    <p />

    <% 
      String action = request.getParameter("action");

      if ("delete".equals(action))
      {
        int id = Integer.valueOf(request.getParameter("id"));

        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        GroupsDelete wxreq1 = (GroupsDelete)factory.instantiateWithToken(GroupsDelete.class);

        //wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setId(id);

        GroupsDeleteResponse wxresp1 = wxreq1.build().execute();

        out.println(wxresp1.getJson());
      }

      if (action != null)
      {
        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        GroupsGet wxreq1 = (GroupsGet)factory.instantiateWithToken(GroupsGet.class);

        wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());

        GroupsGetResponse wxresp1 = wxreq1.build().execute();
        for (GroupsGetResponse.Group group:wxresp1)
        {
    %>
      <p>
      <form>
        <input name="id" value="<%=group.id%>" readonly />
        <input value="<%=group.name%>" readonly />
        <input value="<%=group.count%>" readonly />
        <input name="action" type="submit" value="delete" />
      </form>
      </p>

    <%
        }
      }
    %>
  </body>
</html>
