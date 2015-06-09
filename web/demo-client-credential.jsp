<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.util.Properties,
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
    <h1>微信 Access Token 样例</h1>

    您确定
    <br />
    今天吻过您爱的人了吗?

    <p />
    
    您确定
    <br />
    您的 Access Token 被接管真的大丈夫吗?

    <p />
    
    <span style="color:red;">警告: 暴露此页面等同于暴露您对微信服务器的会话密钥. 请勿在生产环境保留此页面.</span>
    <br />
    <span style="color:red;">警告: 接管 Access Token 会使公众号在其他平台上的绑定暂时失效, 如果它们没有正确处理异常的话.</span>
    <br />
    <span style="color:red;">...以及, 上面这行对所有 demo 而言都是正确的.</span>

    <p />
    
    <form id="form" enctype="application/x-www-form-urlencoded">
      <input name="confirm" type="hidden" value="true"/>
      <input type="submit" value="朕知道了" />
    </form>

    <p />

    <% 
      if (Boolean.TRUE.equals(Boolean.valueOf(request.getParameter("confirm"))))
      {
        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        TokenClientCredential req = (TokenClientCredential)factory.instantiate(TokenClientCredential.class);

        TokenClientCredentialResponse resp = req.build().execute();

        out.println(resp.getJson());
      }
    %>

  </body>
</html>
