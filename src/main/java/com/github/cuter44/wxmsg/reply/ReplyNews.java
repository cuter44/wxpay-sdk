package com.github.cuter44.wxmsg.reply;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

public class ReplyNews extends WxmsgReplyBase
{
  // CONSTANT
    public static final String KEY_ARTICLES         = "Articles";
    public static final String KEY_ARTICLE_COUNT    = "ArticleCount";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "ToUserName",
        "FromUserName",
        "CreateTime",
        "MsgType",
        "ArticleCount",
        "Articles"
    );

  // CONSTRUCT
    public ReplyNews()
    {
        super();

        super.setMsgType(MsgType.news);

        return;
    }

    public ReplyNews(WxmsgBase msg)
    {
        super(msg);

        super.setMsgType(MsgType.news);

        return;
    }

  // BUILD
    /**
     * @throws IllegalStateException If no articles set.
     */
    @Override
    public ReplyNews build()
    {
        if (this.size() == 0)
            throw(new IllegalStateException("0 Articles in place."));

        StringBuilder sb = new StringBuilder(1024);
        for (Article a:this.articles)
            sb.append(a.toContent());

        this.setProperty(KEY_ARTICLES, sb.toString());
        this.setProperty(KEY_ARTICLE_COUNT, Integer.toString(this.size()));

        return(this);
    }

    @Override
    public String toContent()
    {
        return(
            super.buildXMLBody(KEYS_PARAM_NAME)
        );
    }

  // ACCESSOR
    public List<Article> articles;

    public ReplyNews setArticles(List<Article> as)
    {
        this.articles = as;

        return(this);
    }

    /** Append articles to list. If list is not yet created, create it,
     * Not thread-safe.
     */
    public ReplyNews add(Article a)
    {
        if (this.articles == null)
            this.articles = new ArrayList<Article>();

        this.articles.add(a);

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
        public Properties p;

        public static final String KEY_TITLE        = "Title";
        public static final String KEY_DESCRIPTION  = "Description";
        public static final String KEY_PIC_URL      = "PicUrl";
        public static final String KEY_URL          = "Url";
        public static final String[] PARAMS         = {KEY_TITLE, KEY_DESCRIPTION, KEY_PIC_URL, KEY_URL};

        public Article setTitle(String title)
        {
            if (title!=null)
                this.p.setProperty(KEY_TITLE, title);

            return(this);
        }

        public Article setDescription(String description)
        {
            if (description!=null)
                this.p.setProperty(KEY_DESCRIPTION, description);

            return(this);
        }

        public Article setPicUrl(String picUrl)
        {
            if (picUrl!=null)
                this.p.setProperty(KEY_PIC_URL, picUrl);

            return(this);
        }

        public Article setUrl(String url)
        {
            if (url!=null)
                this.p.setProperty(KEY_URL, url);

            return(this);
        }

        public Article()
        {
            p = new Properties();

            return;
        }

        public Article(Properties p)
        {
            this.p = p;

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

        public String toContent()
        {
            StringBuilder xml = new StringBuilder(256);

            xml.append("<item>");

            for (String k:PARAMS)
            {
                String v = this.p.getProperty(k);
                if (v != null)
                    xml.append('<').append(k).append('>')
                       .append(v)
                       .append("</").append(k).append('>');
            }

            xml.append("</item>");

            return(xml.toString());
        }
    }
}
