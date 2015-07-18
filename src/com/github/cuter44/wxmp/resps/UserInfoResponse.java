package com.github.cuter44.wxmp.resps;

import java.util.Date;

import com.alibaba.fastjson.*;

/** 获取用户基本信息(UnionID机制)
 * <br />
 * <pre style="font-size:12px">
    返回说明
    <i>inherited from SnsUserinfoResponse</i>
    openid      用户的唯一标识
    nickname    用户昵称
    sex         用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    province    用户个人资料填写的省份
    city        普通用户个人资料填写的城市
    country     国家，如中国为CN
    headimgurl  用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    privilege   用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    unionid     只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
    <i>Available in UserInfoResponse</i>
    language 	    用户的语言，简体中文为zh_CN
    subscribe       用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    subscribe_time  用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    remark          公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
    gorupid         用户所在的分组ID
 * </pre>
 */
public class UserInfoResponse extends SnsUserinfoResponse
{
  // CONSTANTS
    public static final String KEY_SUBSCRIBE        = "subscribe";
    public static final String KEY_SUBSCRIBE_TIME   = "subscribe_time";
    public static final String KEY_GROUPID          = "groupid";

  // CONSTRUCT
    public UserInfoResponse(String jsonString)
    {
        super(jsonString);

        return;
    }

  // ACCESSOR
    public Boolean getSubscribe()
    {
        return(
            "1".equals(super.getProperty(KEY_SUBSCRIBE))
        );
    }

    public Date getSubscribeTime()
    {
        return(
            new Date(
                Long.valueOf(
                    super.getProperty(KEY_SUBSCRIBE_TIME)
                )*1000L
            )
        );
    }

    public Integer getGroupid()
    {
        return(
            Integer.valueOf(
                super.getProperty(KEY_SUBSCRIBE_TIME)
            )
        );
    }

}
