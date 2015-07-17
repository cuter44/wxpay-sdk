package com.github.cuter44.wxmsg;

import com.github.cuter44.wxmsg.msg.WxmsgBase;

public interface WxmsgHandler
{
    /**
     * @return true if <code>msg</code> consider consumed
     */
    public boolean handle(WxmsgBase msg)
        throws Exception;
}
