<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.util.Properties,
    com.github.cuter44.wxcp.*,
    com.github.cuter44.wxcp.reqs.*,
    com.github.cuter44.wxcp.resps.*
  "
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>

  <body>
    <h1>WEB取得客户端身份样例 | Userid与openid互换样例</h1>
    <p />
    <form id="form" enctype="application/x-www-form-urlencoded">
    <% 
      String action = request.getParameter("action");
      String userid = request.getParameter("userid");
      String openid = request.getParameter("openid");

      if ("ToOpenid".equals(action))
      {
        WxcpFactory factory = WxcpFactory.getDefaultInstance();

        ConvertToOpenid wxreq1 = (ConvertToOpenid)factory.instantiateWithToken(ConvertToOpenid.class);

        ConvertToOpenidResponse wxresp1 = wxreq1.setUserid(userid).build().execute();

        openid = wxresp1.getOpenid();
      }
    %>
    <table>
      <tr>
        <td>userid</td>
        <td><input id="userid" name="userid" value="<%=userid%>"/></td>
        <td><input type="submit" name="action" value="ToOpenid"/></td>
      </tr>
      <tr>
        <td>openid</td>
        <td><input id="openid" name="openid"  value="<%=openid%>"/></td>
        <td><input type="submit" name="action" value="ToUserid"/></td>
      </tr>
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
        var openid = getParamValue("openid") || getParamValue("OpenId");
        var userid = getParamValue("userid") || getParamValue("UserId");
        if (!(openid || userid))
        {
          var thisUrl = location.href;
          location.href="corp-snsapi-base.api?redir="+encodeURIComponent(thisUrl);
        }
        else
        {
          document.getElementById("openid").value = openid;  
          document.getElementById("userid").value = userid;  
        }
      })();
    </script>
  </body>
</html>
