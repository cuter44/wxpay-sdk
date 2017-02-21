<%@ page language="java"  pageEncoding="UTF-8" 
  import="
    java.io.*,
    com.alibaba.fastjson.*,
    com.github.cuter44.wxmp.*,
    com.github.cuter44.wxmp.reqs.*,
    com.github.cuter44.wxmp.resps.*,
    com.github.cuter44.nyafx.crypto.Base64
  "
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  </head>

  <body>
    <h1>上传临时素材-图像</h1>
    <span color="red">不要在生产环境保留此页面, 会暴露 access_token</span>

    <p />

    <form id="form" enctype="application/x-www-form-urlencoded" method="post">
      图像...的 data-url:
      <br />
      <span style="color:gray; font-size:small;">因为不想引入不必要的依赖性<span style="background-color:gray">以及作者懒癌发作</span>所以要用data-url</span>
      <br />
      <span style="color:gray; font-size:small;">转换 data-url 的工具可以在<a href="http://www.atool.org/img2base64.php">这里↗</a>找到</span>
      <br />
      <textarea style="height:480px; width:640px;" name="file">data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcg
SlBFRyB2ODApLCBxdWFsaXR5ID0gOTAK/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwK
DAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQU
FBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgAgACA
AwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMF
BQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkq
NDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqi
o6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/E
AB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMR
BAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVG
R0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKz
tLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A
/SCiiigAooooAKKKKAKes6tbaFpN5qV5IIrW0heeVz/Cqgkn8hXzXHdjWvCXiXUdTz/bPiaxub7D
/wDLOBUxDGPQKhB/E969W+OF3Je6Vo/ha3G6fxDfLbyc422yAyzN/wB8pt/4FXm/jCIy+LI7dFVI
zpt7EkYbA2+Q2Bjt0FAHvfhDUX1fwpo19Iu2S6soZmX0LICR+ta9eOfDX426Vc/Drw7INM1u7kjs
IYpzaaZLIqyLGoYDjLDOcEDmux0n4w+D9XvY7JNcgs9QcAiy1ANaz89tkoUk/SgDsqKAc0UAFFcr
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</textarea>
      <br />
      <input type="submit" />
    </form>

    <p />

    <% 
      String file = request.getParameter("file");
      if (file != null)
      {
        String[] s0 = file.split(";");
        String contentType = s0[0].replaceFirst("data\\:", "");
        String strContent = s0[1].replaceFirst("base64\\,", "");
        System.out.println(strContent.substring(0, 100));

        byte[] binContent = Base64.decode(strContent);
        ByteArrayInputStream streamContent = new ByteArrayInputStream(binContent);

        WxmpFactory factory = WxmpFactory.getDefaultInstance();

        MediaUploadImage wxreq1 = (MediaUploadImage)factory.instantiate(MediaUploadImage.class);
        wxreq1.setAccessToken(factory.getTokenKeeper().getAccessToken());
        wxreq1.setFilename("file.jpg");
        wxreq1.setMedia(streamContent);
        wxreq1.setContentType(contentType);

        MediaUploadResponse wxresp1 = wxreq1.build().execute();
        JSONObject prop1 = wxresp1.getJson();
        System.out.println(prop1);

        out.println(prop1);
        out.println("<br />");

        String accessToken = factory.getTokenKeeper().getAccessToken();
        String mediaId = wxresp1.getMediaId();

    %>
      <img src="https://api.weixin.qq.com/cgi-bin/media/get?access_token=<%=accessToken%>&media_id=<%=mediaId%>" />
    <%
      }
    %>

  </body>
</html>
