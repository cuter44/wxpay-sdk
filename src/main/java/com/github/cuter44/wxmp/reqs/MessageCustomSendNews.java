package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;
import com.alibaba.fastjson.*;

/** 客服接口-发消息(图文)
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    是  调用接口凭证
    touser          是  普通用户openid
    msgtype         是  消息类型，<del>文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，</del>图文消息为news，<del>卡券为wxcard</del>
    <del>content         是  文本消息内容<>
    <del>media_id        是  发送的图片/语音/视频的媒体ID</del>
    <del>thumb_media_id  是  缩略图的媒体ID</del>
    title           否  图文消息/视频消息/音乐消息的标题
    description     否  图文消息/视频消息/音乐消息的描述
    <del>musicurl        是  音乐链接</del>
    <del>hqmusicurl      是  高品质音乐链接，wifi环境优先使用该链接播放音乐</del>
    url             否  图文消息被点击后跳转的链接
    picurl          否  图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
   </pre>
 *
 */
public class MessageCustomSendNews extends MessageCustomSend
{
  // KEYS
    public static final String VALUE_MSGTYPE_NEWS = "news";

    public static final String KEY_CONTENT = "content";
    public static final String KEY_NEWS = "news";
    public static final String KEY_ARTICLES = "articles";

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'touser':{'type':'string'},"+
            "'msgtype':{'type':'string'}"+
        "} }"
    );

  // CONSTRUCT
    public MessageCustomSendNews(Properties prop)
    {
        super(prop);

        super.setProperty(KEY_MSGTYPE, VALUE_MSGTYPE_NEWS);

        return;
    }

  // BUILD
    @Override
    public MessageCustomSendNews build()
    {
        this.jsonBody = super.buildJSONBody(BODY_SCHEMA, this.conf);

        JSONObject t = new JSONObject();
        t.put(KEY_ARTICLES, this.articles);

        this.jsonBody.put(KEY_NEWS, t);

        return(this);
    }

  // EXECUTE
    @Override
    public MessageCustomSendResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new MessageCustomSendResponse(respJson));
    }

  // MISC
    public MessageCustomSendNews setContent(String content)
    {
        super.setProperty(KEY_CONTENT, content);

        return(this);
    }

  // ACCESSOR
    public JSONArray articles;

    public MessageCustomSendNews setArticles(JSONArray as)
    {
        this.articles = as;

        return(this);
    }

    public MessageCustomSendNews setArticles(List<Article> as)
    {
        JSONArray ja = new JSONArray(as.size());
        ja.addAll(as);

        this.setArticles(ja);

        return(this);
    }

    /** Append articles to list. If list is not yet created, create it,
     * Not thread-safe.
     */
    public MessageCustomSendNews add(JSONObject a)
    {
        if (this.articles == null)
            this.articles = new JSONArray();

        this.articles.add(a);

        return(this);
    }

    /** Append articles to list. If list is not yet created, create it,
     * Not thread-safe.
     */
    public MessageCustomSendNews add(Article a)
    {
        this.add(a.toJSON());

        return(this);
    }

    /** Return article
     * Null-safe.
     */
    public int size()
    {
        return(
            this.articles!=null ? this.articles.size() : 0
        );
    }

  // ARTICLES
    public static class Article
    {
        public JSONObject p;

        public static final String KEY_TITLE        = "title";
        public static final String KEY_DESCRIPTION  = "description";
        public static final String KEY_PICURL       = "picurl";
        public static final String KEY_URL          = "url";

        public Article setTitle(String title)
        {
            if (title!=null)
                this.p.put(KEY_TITLE, title);

            return(this);
        }

        public Article setDescription(String description)
        {
            if (description!=null)
                this.p.put(KEY_DESCRIPTION, description);

            return(this);
        }

        public Article setPicUrl(String picurl)
        {
            if (picurl!=null)
                this.p.put(KEY_PICURL, picurl);

            return(this);
        }

        public Article setUrl(String url)
        {
            if (url!=null)
                this.p.put(KEY_URL, url);

            return(this);
        }

        public Article()
        {
            p = new JSONObject();

            return;
        }

        public Article(Properties prop)
        {
            this.p = new JSONObject(prop.size());

            for (String k:prop.stringPropertyNames())
                this.p.put(k, prop.get(k));

            return;
        }

        public Article(String title, String description, String picUrl, String url)
        {
            this();

            this.setTitle(title);
            this.setDescription(description);
            this.setPicUrl(picUrl);
            this.setUrl(url);

            return;
        }

        public JSONObject toJSON()
        {
            return(
                this.p
            );
        }
    }
}
