package com.github.cuter44.wxmp.reqs;

import java.util.Properties;

/** 新增临时(图像)
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/5/963fc70b80dc75483a271298a76a8d59.html">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    是  调用接口凭证
    type            是  媒体文件类型，分别有图片（image）<del>、语音（voice）、视频（video）和缩略图（thumb）</del>
    media           是  form-data中媒体文件标识，有filename、filelength、content-type等信息
        content-type    content-type
        filename        文件名, 缺省为 "file"
   </pre>
 * This is a general super class for uploading message, use corresponding sub-class for specific message type
 */
public class MediaUploadImage extends MediaUpload
{
  // COSTRUCT
    public MediaUploadImage(Properties prop)
    {
        super(prop);

        super.setType("image");

        return;
    }
}
