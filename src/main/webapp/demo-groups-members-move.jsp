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
    <h1>微信设置用户分组样例</h1>

    <p>
      <a target="_blank" href="demo-groups-get.jsp">查询分组id</a>
    </p>

    <form id="form" enctype="application/x-www-form-urlencoded" method="post">
    <table>
      <tr><td>openid</td><td><input id="openid" name="openid" size="32" /><button type="button" onclick="javascript:getOpenid(event)">acquire</button></td></tr>
      <tr><td>groupid</td><td><input id="groupid" name="groupid" value="0"/></td></tr>
      <tr><td></td><td><input type="submit"></td></tr>
    </table>
    </form>
    <p />

    <% 
      String openid = request.getParameter("openid");
      String groupid = request.getParameter("groupid");

      if ((openid!=null) && (groupid!=null))
      {
        int id = Integer.valueOf(request.getParameter("groupid"));

        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        GroupsMembersBatchupdate wxreq1 = (GroupsMembersBatchupdate)factory.instantiateWithToken(GroupsMembersBatchupdate.class);

        //wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setToGroupid(id);
        wxreq1.getOpenidList().add(openid);

        GroupsMembersBatchupdateResponse wxresp1 = wxreq1.build().execute();

        out.println(wxresp1.getJson());
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

        ev || ev.preventDefault();
      }

      document.getElementById("openid").value = getParamValue("openid") || "";  
          
    </script>
  </body>
</html>
