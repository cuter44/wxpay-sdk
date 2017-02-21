package com.github.cuter44.wxmp.menu;

import com.alibaba.fastjson.*;

public class ViewLimited extends JSONObject
    implements SubMenuElement
{
    public static final String KEY_NAME = "name";
    public static final String KEY_MEDIA_ID = "media_id" ;

    public ViewLimited()
    {
        this.put("type", "view_limited");

        return;
    }

    public ViewLimited(String name, String mediaId)
    {
        this.put(KEY_NAME, name);
        this.put(KEY_MEDIA_ID, mediaId);

        return;
    }


    public ViewLimited setName(String name)
    {
        this.put(KEY_NAME, name);

        return(this);
    }

    public ViewLimited setViewLimited(String mediaId)
    {
        this.put(KEY_MEDIA_ID, mediaId);

        return(this);
    }

    @Override
    public String getName()
    {
        return(this.getString(KEY_NAME));
    }
}
