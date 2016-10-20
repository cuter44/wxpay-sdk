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
    <h1>微信自定义菜单查询/设置样例</h1>

    <p>
    使用 query 可以取得当前的菜单设定, 一定程度上可以起到备份的作用, 方便玩坏之后覆盖回去. 使用 set 就会将菜单设定为右侧 textarea 的内容.
    <br />
    com.github.cuter44.wxmp.menu 提供各种菜单组件用以构建菜单, 不过既然是 json 还是由前端操作比较快捷呢 (这个只是 demo 就不要吐槽了
    <br />
    ...其余的您就自己试吧, 玩坏了我不负责. ((( →,)
    </p>

    <p>

    <form id="form" accept-charset="utf-8" method="post">
      <div id="wrapper" style="height:800px; width:100%">
        <div id="sect-left" style="float:left; min-width:3%; width:64%; height:100%; margin-right:4px;">
          <input type="submit" name="action" value="query" />
          <button onclick="toggleWidth(event)">Toggle Width</button>
          <button onclick="copyContent(event)">Copy →</button>
          
          <br />

          <textarea id="menu-got" name="menuGot" style="height:720px; width:100%; border:none; background-color:#f0f0f0;" readonly>
          <% 
            String action = request.getParameter("action");
            String menuGot = request.getParameter("menuGot");

            if ("query".equals(action))
            {
              WxmpFactory factory = WxmpFactory.getDefaultInstance();

              MenuGet wxreq1 = (MenuGet)factory.instantiateWithToken(MenuGet.class);

              MenuGetResponse wxresp1 = wxreq1.build().execute();

              out.print(
                JSON.toJSONString(wxresp1.asJSON(), true)
              );
            }
            else if (menuGot != null)
            {
              out.print(menuGot);
            }
          %>
          </textarea>
        </div>
        <div id="sect-right" style="float:left; min-width:3%; width:32%; height:100%; margin-left:4px;">
          <input type="submit" name="action" value="set" />
          <button onclick="fillDefault(event)">Fill Demo ↓</button>
          <code>
          <%
            String menuSet = request.getParameter("menuSet");

            if ("set".equals(action))
            {
              WxmpFactory factory = WxmpFactory.getDefaultInstance();

              MenuCreate wxreq1 = (MenuCreate)factory.instantiateWithToken(MenuCreate.class);

              JSONObject j = JSON.parseObject(menuSet);
              // this does not work, wried.
              //JSONArray button = j.getJSONArray("button");
              String button = j.getJSONArray("button").toString();
              System.out.println(button);
              wxreq1.setButton(button);

              MenuCreateResponse wxresp1 = wxreq1.build().execute();

              out.print(
                wxresp1.getJson()
              );
            }
          %>
          </code>
          <br />
          <textarea id="menu-set" name="menuSet" style="height:720px; width:100%; border:none; background-color:#f0f0f0;">
          <%
            if (menuSet != null)
            {
              out.print(menuSet);
            }
          %>
          </textarea>
        </div>
      </div>
    </form>
    <script>
      var modeWidth = 0;

      function toggleWidth(ev)
      {
        var t = document.getElementById("sect-left").style.width;
        document.getElementById("sect-left").style.width = document.getElementById("sect-right").style.width;
        document.getElementById("sect-right").style.width = t;

        modeWidth = 1-modeWidth;

        ev && ev.preventDefault();
      }

      function copyContent(ev)
      {
        document.getElementById("menu-set").textContent = document.getElementById("menu-got").textContent;

        (!modeWidth) && toggleWidth();

        ev && ev.preventDefault();
      }

      function fillDemo(ev)
      {
        document.getElementById("menu-set").textContent = "{\n\
    \"button\": [\n\
        {\n\
            \"name\":\"default\",\n\
            \"sub_button\": [\n\
                {\n\
                    \"type\":\"click\",\n\
                    \"name\":\"click\",\n\
                    \"key\":\"demo_click\"\n\
                },\n\
                {\n\
                    \"type\":\"view\",\n\
                    \"name\":\"view\",\n\
                    \"url\":\"https://github.com/cuter44/wxpay-sdk\"\n\
                },\n\
                {\n\
                    \"name\": \"location_select\", \n\
                    \"type\": \"location_select\", \n\
                    \"key\": \"demo_location_select\"\n\
                }\n\
            ]\n\
        },\n\
        {\n\
            \"name\": \"scancode\", \n\
            \"sub_button\": [\n\
                {\n\
                    \"type\": \"scancode_waitmsg\", \n\
                    \"name\": \"scancode_waitmsg\", \n\
                    \"key\": \"demo_scancode_waitmsg\", \n\
                }, \n\
                {\n\
                    \"type\": \"scancode_push\", \n\
                    \"name\": \"scancode_push\", \n\
                    \"key\": \"demo_scancode_push\", \n\
                }\n\
            ]\n\
        }, \n\
        {\n\
            \"name\": \"image\", \n\
            \"sub_button\": [\n\
                {\n\
                    \"type\": \"pic_sysphoto\", \n\
                    \"name\": \"pic_sysphoto\", \n\
                    \"key\": \"demo_pic_sysphoto\", \n\
                }, \n\
                {\n\
                    \"type\": \"pic_photo_or_album\", \n\
                    \"name\": \"pic_photo_or_album\", \n\
                    \"key\": \"demo_pic_photo_or_album\", \n\
                },\n\
                    \n\
                {\n\
                    \"type\": \"pic_weixin\", \n\
                    \"name\": \"pic_weixin\", \n\
                    \"key\": \"key_pic_weixin\", \n\
                }\n\
            ]\n\
        }\n\
    ]\n\
}";

        (!modeWidth) && toggleWidth();

        ev && ev.preventDefault();
      }

      <%
        if ("set".equals(action))
          out.println("document.onload=function(){ toggleWidth(); }");
      %>
    </script>

    </p>

  </body>
</html>
