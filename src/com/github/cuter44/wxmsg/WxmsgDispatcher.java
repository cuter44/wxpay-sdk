package com.github.cuter44.wxmsg;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.constants.MsgType;

public class WxmsgDispatcher
    implements WxmsgHandler
{
    protected List<WxmsgHandler> preprocessors;
    protected Map<MsgType, List<WxmsgHandler>> handlers;
    protected List<WxmsgHandler> fallbacks;
    protected List<WxmsgHandler> postprocessors;

  // CONSTRUCT
    public WxmsgDispatcher()
    {
        this.handlers = new HashMap<MsgType, List<WxmsgHandler>>();

        this.preprocessors = new LinkedList<WxmsgHandler>();
        this.fallbacks = new LinkedList<WxmsgHandler>();
        this.postprocessors = new LinkedList<WxmsgHandler>();

        return;
    }

  // SINGLETON
    private static final class Singleton
    {
        public static WxmsgDispatcher instance = new WxmsgDispatcher();
    }

    public static WxmsgDispatcher getDefaultInstance()
    {
        return(Singleton.instance);
    }

  // LISTENER
    public synchronized WxmsgDispatcher subscribe(MsgType type, WxmsgHandler h)
    {
        switch (type)
        {
            case PREPROCESS:
                this.preprocessors.add(h);
                break;
            case FALLBACK:
                this.fallbacks.add(h);
                break;
            case POSTPROCESS:
                this.postprocessors.add(h);
                break;
            default:
                List<WxmsgHandler> l = this.handlers.get(type);
                if (l == null)
                {
                    l = new LinkedList<WxmsgHandler>();
                    this.handlers.put(type, l);
                }
                l.add(h);
                break;
        }

        return(this);
    }


  // DISPATCH
    @Override
    public boolean handle(WxmsgBase msg)
        throws Exception
    {
        // ECHO
        if (MsgType.ECHO.equals(msg.getMsgType()))
            return(this.handleEcho(msg));

        // PREPROCESS
        for (WxmsgHandler h:this.preprocessors)
            h.handle(msg);

        // STANDARD
        boolean handled = false;
        List<WxmsgHandler> l = this.handlers.get(msg.getMsgType());
        if (l != null)
            for (WxmsgHandler h:l)
            {
                handled = h.handle(msg);
                if (handled) break;
            }

        // FALLBACK
        if (!handled)
            for (WxmsgHandler h:this.fallbacks)
            {
                handled = h.handle(msg);
                if (handled) break;
            }

        // POSTPROCESS
        for (WxmsgHandler h:this.postprocessors)
            h.handle(msg);

        return(handled);
    }

    /** Handles echo request
     * Handlers registered in ECHO channel are invoked sequentailly, and stopped on once a handler returns true.
     * EchoHandler are responsible to verify the parameters to judge whether to accept the connection, but not responsible to generate reply.
     * Echos will NOT trigger PREPROCESS/FALLBACK/POSTPROCESS channel.
     */
    public boolean handleEcho(WxmsgBase echo)
        throws Exception
    {
        boolean handled = false;
        List<WxmsgHandler> l = this.handlers.get(MsgType.ECHO);
        if (l != null)
            for (WxmsgHandler h:l)
            {
                handled = h.handle(echo);
                if (handled) break;
            }

        return(handled);
    }
}
