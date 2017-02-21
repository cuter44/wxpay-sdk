package com.github.cuter44.wxmp.resps;

import java.util.Date;

import java.io.IOException;
import java.net.MalformedURLException;

import com.alibaba.fastjson.*;

/** 新增临时素材
 * <br />
 * <pre style="font-size:12px">
    返回说明
    type        媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
    media_id    媒体文件上传后，获取时的唯一标识
    created_at  媒体文件上传时间戳<i>注:单位秒</i>
 * </pre>
 */
public class MediaUploadResponse extends WxmpResponseBase
{
  // CONSTANTS
    public static final String KEY_TYPE         = "type";
    public static final String KEY_MEDIA_ID     = "media_id";
    public static final String KEY_CREATED_AT   = "created_at";

  // CONSTRUCT
    public MediaUploadResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public String getType()
    {
        return(
            super.getProperty(KEY_TYPE)
        );
    }

    public String getMediaId()
    {
        return(
            super.getProperty(KEY_MEDIA_ID)
        );
    }

    public Date getCreatedAt()
    {
        return(
            new Date(
                Long.valueOf(super.getProperty(KEY_CREATED_AT))*1000L
            )
        );
    }
}
