package com.github.cuter44.wxpay;

import com.github.cuter44.wxpay.resps.Notify;

public interface WxpayNotifyListener
{
    /**
     * 1. 实现这个方法
     * 2. 添加监听器到网关
     * 3. 网关在收到通知时会顺序回调
     * 4. 返回 true 会阻止回调继续向下传递, 并向微信发送 success.
     * 注意. 通知是并发的请注意自行维护线程安全
     */
    public abstract boolean handle(Notify notice);
}
