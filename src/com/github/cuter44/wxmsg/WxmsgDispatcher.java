package com.github.cuter44.wxmsg;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import com.github.cuter44.wxmsg.msg.WxmsgBase;
import com.github.cuter44.wxmsg.msg.EventBase;
import com.github.cuter44.wxmsg.constants.MsgType;
import com.github.cuter44.wxmsg.constants.EventType;

public class WxmsgDispatcher
    implements WxmsgHandler
{
    protected Map<MsgType, List<WxmsgHandler>> msgHandlers;
    protected Map<EventType, List<WxmsgHandler>> eventHandlers;

    protected List<WxmsgHandler> preprocessors;
    protected List<WxmsgHandler> fallbacks;
    protected List<WxmsgHandler> postprocessors;

  // CONSTRUCT
    public WxmsgDispatcher()
    {
        this.msgHandlers = new HashMap<MsgType, List<WxmsgHandler>>();
        this.eventHandlers = new HashMap<EventType, List<WxmsgHandler>>();

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

  // HANDLER
    /** Register as msg handler
     */
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
                List<WxmsgHandler> l = this.msgHandlers.get(type);
                if (l == null)
                {
                    l = new LinkedList<WxmsgHandler>();
                    this.msgHandlers.put(type, l);
                }
                l.add(h);
                break;
        }

        return(this);
    }

    /** Register as EventHandler
     * <br />
     * While event msg arrived, corresponding type of event handlers are invoked first, then the msg handlers registered in <code>MsgType.event</code> channel.
     * If one of the event handlers reported handled, it suppresss the msg handlers.
     */
    public synchronized WxmsgDispatcher subscribe(EventType type, WxmsgHandler h)
    {
        List<WxmsgHandler> l = this.eventHandlers.get(type);
        if (l == null)
        {
            l = new LinkedList<WxmsgHandler>();
            this.eventHandlers.put(type, l);
        }
        l.add(h);

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

        boolean handled = false;

        // EVENT
        if (MsgType.event.equals(msg.getMsgType()))
        {
            EventBase ev = (EventBase)msg;
            List<WxmsgHandler> l = this.eventHandlers.get(msg.getMsgType());
            if (l != null)
                for (WxmsgHandler h:l)
                {
                    handled = h.handle(msg);
                    if (handled) break;
                }
        }

        // MSG
        List<WxmsgHandler> l = this.msgHandlers.get(msg.getMsgType());
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
        List<WxmsgHandler> l = this.msgHandlers.get(MsgType.ECHO);
        if (l != null)
            for (WxmsgHandler h:l)
            {
                handled = h.handle(echo);
                if (handled) break;
            }

        return(handled);
    }
}
