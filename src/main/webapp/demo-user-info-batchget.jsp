<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.util.List,
    java.util.Arrays,
    com.alibaba.fastjson.*,
    com.github.cuter44.wxmp.*,
    com.github.cuter44.wxmp.reqs.*,
    com.github.cuter44.wxmp.resps.*
  "
%>
<%
  String openids = request.getParameter("openid");
  String lang = request.getParameter("lang");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>

  <body>
    <h1>微信批量获取用户信息样例</h1>

    <form id="form" enctype="application/x-www-form-urlencoded" method="post">
    <table>
      <tr><td>openids</td><td><input id="openid" name="openid" size="128" required value="<%=openids!=null?openids:""%>"/><button type="button" onclick="javascript:getOpenid(event)">acquire</button></td></tr>
      <tr><td></td><td>使用 , 分隔</td></tr>
      <tr><td>lang</td><td><input id="lang" name="lang" value="<%=lang!=null?lang:"zh_CN"%>"/></td></tr>
      <tr><td></td><td><input type="submit"></td></tr>
    </table>
    </form>
    <p />

    <% 
      if (openids!=null)
      {
        List<String> openidList = Arrays.asList(openids.split(","));

        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        UserInfoBatchget wxreq1 = (UserInfoBatchget)factory.instantiateWithToken(UserInfoBatchget.class);

        //wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setOpenidList(openidList);
        wxreq1.setLang(lang);

        UserInfoBatchgetResponse wxresp1 = wxreq1.build().execute();

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
