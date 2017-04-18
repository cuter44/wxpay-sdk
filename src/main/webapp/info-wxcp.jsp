<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" 
  import="
    java.util.Properties,
    java.util.Date,
    com.github.cuter44.wxcp.*,
    com.github.cuter44.wxcp.util.*,
    com.github.cuter44.wxcp.reqs.*,
    com.github.cuter44.wxcp.resps.*
  "
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>WXCP Singl Probe</title>
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
  <h1>WXCP Singl Probe</h1>
  <p />
  <span style="alert">WARNING: This page shows confidential info of your config. DO NOT publish this page on production environment</span>

  <h3>Global config</h3>
  <dl class="table">
    <%
      WxcpFactorySingl wxcp = WxcpFactorySingl.getInstance();
      Properties wxcpConf = wxcp.getConf();

      for (String k:wxcpConf.stringPropertyNames())
        out.println("<dt>"+k+"<dd>"+wxcpConf.getProperty(k));
    %>
  </dl>
  
  <br class="clearfix" />
  <h3>Tokens</h3>
  <dl class="table">
    <%
      TokenProviderCp tp = wxcp.getTokenProvider();
    %>
    <dt>TokenProviderCp class
    <dd><%=tp.getClass()%>

    <dt>corpid
    <dd><%=tp.getCorpid()%>

    <dt>corpsecret
    <dd><%=tp.getCorpsecret()%>

    <dt>access_token
    <dd><%=tp.getAccessToken()%>

    <dt>access_token expire
    <dd><%=new Date(tp.getATExpire())%>

    <dt>jsapi ticket
    <dd><%=tp.getJsapiTicket()%>

    <dt>jsapi ticket expire
    <dd><%=new Date(tp.getJTExpire())%>

  </dl>

 </body>
</html>
