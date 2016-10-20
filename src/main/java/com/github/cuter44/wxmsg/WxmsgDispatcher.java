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

    /** You are not overwriting as it is short-cut to <code>this.msgHandlers.get(MsgType.PREPOCESS)</code> since 0.7.1.2.
     * Unless you read and comprehened the source.
     */
    protected List<WxmsgHandler> preprocessors;
    /** You are not overwriting as it is short-cut to <code>this.msgHandlers.get(MsgType.FALLBACK)</code> since 0.7.1.2.
     * Unless you read and comprehened the source.
     */
    protected List<WxmsgHandler> fallbacks;
    /** You are not overwriting as it is short-cut to <code>this.msgHandlers.get(MsgType.POSTPOCESS)</code> since 0.7.1.2.
     * Unless you read and comprehened the source.
     */
    protected List<WxmsgHandler> postprocessors;

  // CONSTRUCT
    public WxmsgDispatcher()
    {
        this.msgHandlers = new HashMap<MsgType, List<WxmsgHandler>>();
        for (MsgType t:MsgType.values())
            this.msgHandlers.put(t, new LinkedList<WxmsgHandler>());

        this.eventHandlers = new HashMap<EventType, List<WxmsgHandler>>();
        for (EventType t:EventType.values())
            this.eventHandlers.put(t, new LinkedList<WxmsgHandler>());

        this.preprocessors = this.msgHandlers.get(MsgType.PREPROCESS);
        this.fallbacks = this.msgHandlers.get(MsgType.FALLBACK);
        this.postprocessors = this.msgHandlers.get(MsgType.POSTPROCESS);

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
        this.msgHandlers.get(type).add(h);

        return(this);
    }

    /** Register as event handler
     * <br />
     * While event msg arrived, corresponding type of event handlers are invoked first, then the msg handlers registered in <code>MsgType.event</code> channel.
     * If one of the event handlers reported handled, it suppresss the msg handlers.
     */
    public synchronized WxmsgDispatcher subscribe(EventType type, WxmsgHandler h)
    {
        this.eventHandlers.get(type).add(h);

        return(this);
    }


  // DISPATCH
    @Override
    public boolean handle(WxmsgBase msg)
        throws Exception
    {
        // PREPROCESS
        for (WxmsgHandler h:this.preprocessors)
            h.handle(msg);

        boolean handled = false;

        switch (msg.getMsgType())
        {
            // ECHO
            case ECHO:
                handled = this.handleEcho(msg);
                break;

            // EVENT
            case event:
                EventBase ev = (EventBase)msg;
                List<WxmsgHandler> lEvent = this.eventHandlers.get(ev.getEventType());
                for (WxmsgHandler h:lEvent)
                {
                    handled = h.handle(ev);
                    if (handled) break;
                }

                if (handled) break;

            // MSG
            default:
                List<WxmsgHandler> lMsg = this.msgHandlers.get(msg.getMsgType());
                for (WxmsgHandler h:lMsg)
                {
                    handled = h.handle(msg);
                    if (handled) break;
                }
                break;
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
     * <code>handle()</code> invoke this method. Developers are able to directly call this method to skip the standard PRE/FALLBACK/POST process stages.
     */
    public boolean handleEcho(WxmsgBase echo)
        throws Exception
    {
        boolean handled = false;
        List<WxmsgHandler> l = this.msgHandlers.get(MsgType.ECHO);
        for (WxmsgHandler h:l)
        {
            handled = h.handle(echo);
            if (handled) break;
        }

        return(handled);
    }
}
