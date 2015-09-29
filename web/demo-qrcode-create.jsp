<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.io.*,
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
    <h1>生成带参数的二维码</h1>

    <p />

    <form id="form" method="GET">
      <table>
      <tr>
        <td>action_name</td>
        <td>
          <table>
            <tr><td><input type="radio" id="QR_SCENE" name="actionName" value="QR_SCENE" onclick="javascript:actionNameSelect(event)"/> QR_SCENE(限时, int id)</td></tr>
            <tr><td><input type="radio" id="QR_LIMIT_SCENE" name="actionName" value="QR_LIMIT_SCENE" onclick="javascript:actionNameSelect(event)"/> QR_LIMIT_SCENE(永久, int id)</td></tr>
            <tr><td><input type="radio" id="QR_LIMIT_STR_SCENE" name="actionName" value="QR_LIMIT_STR_SCENE" onclick="javascript:actionNameSelect(event)"/> QR_LIMIT_SCENE(永久, str id)</td></tr>
          </table>
        </td>
      </tr>
      <tr><td>expire_seconds</td><td><input id="expire-seconds" name="expireSeconds" value="604800"/></td></tr>
      <tr><td>scene_id</td><td><input id="scene-id" name="sceneId" /></td></tr>
      <tr><td>scene_str</td><td><input id="scene-str" name="sceneStr" /></td></tr>
      <tr><td></td><td><input type="submit" /></td></tr>
      </table>
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

        (function(){
          var ran = Math.random().toString().substr(3, 8);
          document.getElementById("scene-id").value = getParamValue("sceneId") || ran;
          document.getElementById("scene-str").value = getParamValue("sceneStr") || "demo-"+ran;
        })();

        function actionNameSelect(ev)
        {
          var selected = ev.target.id;
          var expireSeconds = document.getElementById("expire-seconds");
          var sceneId = document.getElementById("scene-id");
          var sceneStr = document.getElementById("scene-str");
          
          expireSeconds.disabled = sceneId.disabled = sceneStr.disabled = true;
          expireSeconds.required = sceneId.required = sceneStr.required = false;
          
          switch (selected)
          {
            case "QR_SCENE":
              expireSeconds.disabled = sceneId.disabled = false;
              expireSeconds.required = sceneId.required = true;
              break;
            case "QR_LIMIT_SCENE":
              sceneId.disabled = false;
              sceneId.required = true;
              break;
            case "QR_LIMIT_STR_SCENE":
              sceneStr.disabled = false;
              sceneStr.required = true;
              break;
          }
        }
      </script>
    </form>

    <p />

    <% 
      String actionName = request.getParameter("actionName");
      if (actionName != null)
      {
        String  expireSeconds = request.getParameter("expireSeconds");
                expireSeconds = (expireSeconds!=null) ? expireSeconds : "";
        String  sceneId = request.getParameter("sceneId");
                sceneId = (sceneId!=null) ? sceneId : "";
        String  sceneStr = request.getParameter("sceneStr");
                sceneStr = (sceneStr!=null) ? sceneStr : "";

        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        QrcodeCreate wxreq1 = (QrcodeCreate)factory.instantiate(QrcodeCreate.class);

        wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setActionName(actionName);
        wxreq1.setProperty(QrcodeCreate.KEY_EXPIRE_SECONDS, expireSeconds);
        wxreq1.setProperty(QrcodeCreate.KEY_SCENE_ID, sceneId);
        wxreq1.setSceneStr(sceneStr);

        QrcodeCreateResponse wxresp1 = wxreq1.build().execute();

        JSONObject prop1 = wxresp1.getJson();
        System.out.println(prop1);

        out.println(prop1);

        Showqrcode wxreq2 = new Showqrcode(wxresp1);
        wxreq2.build();
    %>
    <br />
    <img src="<%=wxreq2.toURL()%>">
    <%
      }
    %>
  </body>
</html>
