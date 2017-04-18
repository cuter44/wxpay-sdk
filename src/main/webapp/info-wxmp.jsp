<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" 
  import="
    java.util.Properties,
    java.util.Date,
    com.github.cuter44.wxmp.*,
    com.github.cuter44.wxmp.util.*,
    com.github.cuter44.wxmp.reqs.*,
    com.github.cuter44.wxmp.resps.*
  "
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>WXMP Singl Probe</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <style>
    .alert {
      color:red;
    }

    dl.table > dt {
      clear:both;
      float:left;
      width:400px;
    }

    dl.table > dd {
      float:left;
      max-width:640px;
    }

    .clearfix {
      clear:both;
    }

  </style>
 </head>
 <body>
  <h1>WXMP Singl Probe</h1>
  <p />
  <span style="alert">WARNING: This page shows confidential info of your config. DO NOT publish this page on production environment</span>

  <h3>Global config</h3>
  <dl class="table">
    <%
      WxmpFactorySingl wxmp = WxmpFactorySingl.getInstance();
      Properties wxmpConf = wxmp.getConf();

      for (String k:wxmpConf.stringPropertyNames())
        out.println("<dt>"+k+"<dd>"+wxmpConf.getProperty(k));
    %>
  </dl>
  
  <br class="clearfix" />
  <h3>Tokens</h3>
  <dl class="table">
    <%
      TokenProvider tp = wxmp.getTokenProvider();
    %>
    <dt>TokenProvider class
    <dd><%=tp.getClass()%>

    <dt>appid
    <dd><%=tp.getAppid()%>

    <dt>SECRET
    <dd><%=tp.getSecret()%>

    <dt>access_token
    <dd><%=tp.getAccessToken()%>

    <dt>access_token expire
    <dd><%=new Date(tp.getATExpire())%>

    <dt>JSSDK ticket
    <dd><%=tp.getJSSDKTicket()%>

    <dt>JSSDK ticket expire
    <dd><%=new Date(tp.getJTExpire())%>

  </dl>

 </body>
</html>
