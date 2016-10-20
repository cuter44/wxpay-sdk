<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    com.alibaba.fastjson.*,
    com.github.cuter44.wxmp.*,
    com.github.cuter44.wxmp.reqs.*,
    com.github.cuter44.wxmp.resps.*
  "
%>
<%
  request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>

  <body>
    <h1>微信发送文本样例</h1>
    目标客户端需要在过去24小时内曾与公众号发生交互. (以及前导条件, 此客户端关注公众号)
    <br />
    获取 openid↘ 仅支持服务号, 且需要配置 snsapi-base, 配置方法参见<a href="http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html">网页授权获取用户基本信息↗</a> -&gt; 关于网页授权回调域名的说明.
    <br />
    非服务号参见<del>这篇wiki</del><span style="background-color:black;">还没写</span>
    <p />
    <form id="form" method="post">
    <table>
      <tr><td>发给(openid)</td><td><input id="openid" name="touser" size="32" /><button type="button" onclick="javascript:getOpenid(event)">acquire</button></td></tr>
      <tr><td>文本</td><td><input id="content" name="content" value="喵喵喵 ฅ(・ω・ )ฅ"/></td></tr>
      <tr><td></td><td><input type="submit"></td></tr>
    </table>
    </form>

    <p />

    <% 
      request.setCharacterEncoding("utf-8");

      String touser = request.getParameter("touser");
      String content = request.getParameter("content");
      if ((touser != null) && (content != null))
      {
        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        MessageCustomSendText wxreq1 = new MessageCustomSendText(factory.getConf());
        wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setTouser(touser);
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
