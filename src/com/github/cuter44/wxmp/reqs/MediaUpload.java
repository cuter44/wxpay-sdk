package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.client.methods.*;
import org.apache.http.client.config.RequestConfig;
import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 新增临时素材
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/5/963fc70b80dc75483a271298a76a8d59.html">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    是  调用接口凭证
    type            是  媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
    media           是  form-data中媒体文件标识，有filename、filelength、content-type等信息
        content-type    content-type
        filename        文件名, 缺省为 "file"
   </pre>
 * This is a general super class for uploading media, use corresponding sub-class for specific media type
 */
public abstract class MediaUpload extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token", "type"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_TYPE          = "type";
    public static final String KEY_FILENAME      = "filename";
    public static final String KEY_CONTENT_TYPE  = "content-type";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/media/upload";

    protected InputStream bodyStream;

  // CONSTRUCT
    public MediaUpload(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public MediaUpload build()
    {
        return(this);
    }

  // TO_URL
    @Override
    public String toURL()
    {
        throw(
            new UnsupportedOperationException("This request does not execute on client side.")
        );
    }

  // EXECUTE
    @Override
    public MediaUploadResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);

        String respJson = this.executeMediaUpload(url);

        return(new MediaUploadResponse(respJson));
    }

    protected String executeMediaUpload(String fullURL)
        throws IOException
    {
        HttpPost req = new HttpPost(fullURL);

        // DEBUGING set proxy if need to capture the traffic
        // remember to load your proxy certificate
        //RequestConfig rc = RequestConfig.custom()
            //.setProxy(
                //new HttpHost("10.50.9.21", 8888)
            //)
            //.build();
        //req.setConfig(rc);

        req.setEntity(
            MultipartEntityBuilder.create()
                .addBinaryBody(
                    "media",
                    this.bodyStream,
                    ContentType.create(super.getProperty(KEY_CONTENT_TYPE)),
                    super.getProperty(KEY_FILENAME)
                )
                .build()
        );

        CloseableHttpResponse resp = super.getHttpClient().execute(req);

        String content = getResponseBody(resp);

        resp.close();

        return(content);
    }

  // MISC
    public MediaUpload setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    /**
     * @param type = image|voice|video|thumb
     */
    public MediaUpload setType(String type)
    {
        super.setProperty(KEY_TYPE, type);

        return(this);
    }

    /** Set filename.
     * It is always required to set filename corresponding the file type. I mean, the EXT-NAME.
     * Any breach will lead to a 40005 errcode, which seems weird in Linux philosophy.
     */
    public MediaUpload setFilename(String filename)
    {
        super.setProperty(KEY_FILENAME, filename);

        return(this);
    }

    public MediaUpload setContentType(String contentType)
    {
        super.setProperty(KEY_CONTENT_TYPE, contentType);

        return(this);
    }

    /** Set upload content
     * Do not detect file-type, it is still required to set content-type and filename manually.
     */
    public MediaUpload setMedia(InputStream stream)
    {
        this.bodyStream = stream;

        return(this);
    }

    /** Set upload content
     * Do not detect file-type, it is still required to set content-type manually.
     */
    public MediaUpload setMedia(File file)
        throws FileNotFoundException
    {
        this.bodyStream = new FileInputStream(file);
        this.setFilename(file.getName());

        return(this);
    }

}
