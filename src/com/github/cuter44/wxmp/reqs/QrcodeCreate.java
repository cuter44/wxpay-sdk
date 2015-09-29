package com.github.cuter44.wxmp.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.resps.*;

/** 创建二维码ticket
 * <br />
 * <a href="http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html#.E5.88.9B.E5.BB.BA.E4.BA.8C.E7.BB.B4.E7.A0.81ticket">ref ↗</a>
 * <br />
 * <pre style="font-size:12px">
    参数说明
    expire_seconds  该二维码有效时间，以秒为单位。 最大不超过604800（即7天）。
    action_name     二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
    <del>action_info     二维码详细信息</del>
    scene_id        场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
    scene_str       场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段 * </pre>
 */
public class QrcodeCreate extends WxmpRequestBase
{
  // KEYS
    protected static final List<String> KEYS_PARAM = Arrays.asList(
        "access_token"
    );

    public static final String KEY_ACCESS_TOKEN      = "access_token";
    public static final String KEY_ACTION_NAME       = "action_name";
    public static final String KEY_EXPIRE_SECONDS    = "expire_seconds";
    public static final String KEY_SCENE_ID          = "scene_id";
    public static final String KEY_SCENE_STR         = "scene_str";

    public static final String URL_API_BASE = "https://api.weixin.qq.com/cgi-bin/qrcode/create";

    protected JSONObject jsonBody;

    public static final JSONObject BODY_SCHEMA = JSON.parseObject(
        "{"+
          "'properties':{"+
            "'action_name':{'type':'string'},"+
            "'expire_seconds':{'type':'integer'},"+
            "'action_info':{"+
              "'type':'object',"+
              "'schema':{"+
                "'properties':{"+
                  "'scene':{"+
                    "'type':'object',"+
                    "'schema':{"+
                      "'properties':{"+
                        "'scene_id':{'type':'integer'},"+
                        "'scene_str':{'type':'string'}"+
        "} } } } } } } }"
    );

  // CONSTRUCT
    public QrcodeCreate(Properties prop)
    {
        super(prop);

        return;
    }

  // BUILD
    @Override
    public QrcodeCreate build()
    {
        Properties p = new Properties(this.conf);

        this.jsonBody = super.buildJSONBody(BODY_SCHEMA, p);

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
    public QrcodeCreateResponse execute()
        throws IOException
    {
        String url = URL_API_BASE+"?"+super.toQueryString(KEYS_PARAM);
        String body = this.jsonBody.toString();

        String respJson = super.executePostJSON(url, body);

        return(new QrcodeCreateResponse(respJson));
    }

  // MISC
    public QrcodeCreate setAccessToken(String accessToken)
    {
        super.setProperty(KEY_ACCESS_TOKEN, accessToken);

        return(this);
    }

    public QrcodeCreate setActionName(String actionName)
    {
        super.setProperty(KEY_ACTION_NAME, actionName);

        return(this);
    }

    public String getActionName()
    {
        return(
            super.getProperty(KEY_ACTION_NAME)
        );
    }

    public QrcodeCreate setExpireSeconds(int expireSeconds)
    {
        super.setProperty(KEY_ACTION_NAME, Integer.toString(expireSeconds));

        return(this);
    }

    public QrcodeCreate setSceneId(int sceneId)
    {
        super.setProperty(KEY_SCENE_ID, Integer.toString(sceneId));

        return(this);
    }

    public QrcodeCreate setSceneStr(String sceneStr)
    {
        super.setProperty(KEY_SCENE_STR, sceneStr);

        return(this);
    }

  // CONCRETE
    public static class QrScene extends QrcodeCreate
    {
        public static final String AN_QR_SCENE = "QR_SCENE";

        public QrScene(Properties p)
        {
            super(p);

            super.setProperty(KEY_ACTION_NAME, AN_QR_SCENE);

            return;
        }

        @Override
        public QrScene setSceneStr(String sceneStr)
        {
            throw(new UnsupportedOperationException("QrScene not allowing scene_str."));
        }
    }

    public static class QrLimitScene extends QrcodeCreate
    {
        public static final String AN_QR_LIMIT_SCENE = "QR_LIMIT_SCENE";

        public QrLimitScene(Properties p)
        {
            super(p);

            super.setProperty(KEY_ACTION_NAME, AN_QR_LIMIT_SCENE);

            return;
        }

        @Override
        public QrLimitScene setExpireSeconds(int expireSeconds)
        {
            throw(new UnsupportedOperationException("QrLimitScene not uses expire_seconds."));
        }

        @Override
        public QrLimitScene setSceneStr(String sceneStr)
        {
            throw(new UnsupportedOperationException("QrLimitScene mot allows scene_str."));
        }
    }

    public static class QrLimitStrScene extends QrcodeCreate
    {
        public static final String AN_QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";

        public QrLimitStrScene(Properties p)
        {
            super(p);

            super.setProperty(KEY_ACTION_NAME, AN_QR_LIMIT_STR_SCENE);

            return;
        }

        @Override
        public QrLimitStrScene setSceneId(int sceneId)
        {
            throw(new UnsupportedOperationException("QrLimitStrScene not allows scene_id."));
        }
    }
}
