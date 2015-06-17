package com.github.cuter44.wxmp.menu;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.*;

public class View extends JSONObject
    implements SubMenuElement
{
    public static final int URL_BYTE_SIZE_CONSTRAINT = 256;

    public static final String KEY_NAME = "name";
    public static final String KEY_URL = "url" ;

    public View()
    {
        this.put("type", "view");

        return;
    }

    public View(String name, String url)
    {
        this.put(KEY_NAME, name);
        this.setUrl(url);

        return;
    }


    public View setName(String name)
    {
        this.put(KEY_NAME, name);

        return(this);
    }

    public View setUrl(String url)
    {
        try
        {
            if (url.getBytes("UTF-8").length > URL_BYTE_SIZE_CONSTRAINT)
                throw(new IllegalArgumentException("Url length limit "+URL_BYTE_SIZE_CONSTRAINT+" bytes excessed."));
        }
        catch (UnsupportedEncodingException ex)
        {
            // never occur
            ex.printStackTrace();
        }

        this.put(KEY_URL, url);

        return(this);
    }

    @Override
    public String getName()
    {
        return(this.getString(KEY_NAME));
    }
}
