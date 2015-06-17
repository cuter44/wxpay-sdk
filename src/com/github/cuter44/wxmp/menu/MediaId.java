package com.github.cuter44.wxmp.menu;

import com.alibaba.fastjson.*;

public class MediaId extends JSONObject
    implements SubMenuElement
{
    public static final String KEY_NAME = "name";
    public static final String KEY_MEDIA_ID = "media_id" ;

    public MediaId()
    {
        this.put("type", "media_id");

        return;
    }

    public MediaId(String name, String mediaId)
    {
        this.put(KEY_NAME, name);
        this.put(KEY_MEDIA_ID, mediaId);

        return;
    }


    public MediaId setName(String name)
    {
        this.put(KEY_NAME, name);

        return(this);
    }

    public MediaId setMediaId(String mediaId)
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
