package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.github.cuter44.wxmp.resps.*;
import com.alibaba.fastjson.*;

/** 客服接口-发消息
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    access_token    是  调用接口凭证
    touser          是  普通用户openid
    msgtype         是  消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news，卡券为wxcard
    content         是  文本消息内容
    media_id        是  发送的图片/语音/视频的媒体ID
    thumb_media_id  是  缩略图的媒体ID
    title           否  图文消息/视频消息/音乐消息的标题
    description     否  图文消息/视频消息/音乐消息的描述
    musicurl        是  音乐链接
    hqmusicurl      是  高品质音乐链接，wifi环境优先使用该链接播放音乐
    url             否  图文消息被点击后跳转的链接
    picurl          否  图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80 *
   </pre>
 * This is a general super class for sending message, use corresponding sub-class for specific message type
 */
public abstract class MessageCustomSend extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN  = "access_token";
    public static final String KEY_TOUSER        = "touser";
    public static final String KEY_MSGTYPE       = "msgtype";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/message/custom/send";

    protected JSONObject jsonBody;

  // CONSTRUCT
    public MessageCustomSend(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public MessageCustomSend build()
    {
        throw(
            new UnsupportedOperationException("MessageCustomSend do not build request, instaninate its sub-class instead.")
        );
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
    //@Override
    //public MessageCustomSendResponse execute()
        //throws IOException
    //{
        //String url = URL_API_BASE+"?"+this.toQueryString(KEYS_PARAM);
        //String body = this.jsonBody;

        //String respJson = this.executePostJson(url, body);

        //return(new MessageCustomSendResponse(respJson));
    //}

  // MISC
    public MessageCustomSend setTouser(String openid)
    {
        super.setProperty(KEY_TOUSER, openid);

        return(this);
    }

    public MessageCustomSend setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

}
