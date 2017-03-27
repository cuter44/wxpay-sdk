package com.github.cuter44.wxmp.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

/** Magazine to create/store and recall TokenProvider.
 */
public class ATMag
{
    public static final String KEY_APPID = "appid";
    public static final String KEY_AT_PROVIDER = "AT_PROVIDER";
    public static final Class<ATCore> AT_PROVIDER_DEFAULTS = ATCore.class;

  // CONSTRUCT
    protected Map<String, TokenProvider> keeps;

    public ATMag()
    {
        this.keeps = new HashMap<String, TokenProvider>();

        return;
    }

  // DEFAULT INSTANCE
    private static class DefaultInstance
    {
        public static ATMag instance = new ATMag();
    }

    public static ATMag getDefaultInstance()
    {
        return(DefaultInstance.instance);
    }

  // CREATE
    /** Instantiate a TokenProvider, w/o register to or recall from keep.
     */
    public TokenProvider instantiate(Properties p)
        throws InstantiationException, IllegalAccessException
    {
        Class<? extends TokenProvider> pclass = null;

        try
        {
            pclass = (Class<? extends TokenProvider>)Class.forName(p.getProperty(KEY_AT_PROVIDER));
        }
        catch (Exception ex)
        {
            pclass = AT_PROVIDER_DEFAULTS;
        }

        TokenProvider t = pclass.newInstance();
        t.config(p);

        return(t);
    }

  // KEEP
    /** Register to internal keep, so can recall with appid.
     * if t is not exactly same(==) with a previously registered one, t tweak the old one,
     * and the old's close() is called.
     *
     * @reture t
     */
    public TokenProvider keep(TokenProvider t)
    {
        String appid = t.getAppid();
        TokenProvider t0 = this.keeps.get(appid);

        if (t == t0)
            return(t);

        // else
        synchronized(this.keeps)
        {
            t0 = this.keeps.get(appid);

            if (t0 != null)
                t0.close();

            this.keeps.put(appid, t);
        }

        return(t);
    }

    /** Get registered TokenProvider, or instantiate a new one.
     */
    public TokenProvider getOrCreate(Properties p)
        throws InstantiationException, IllegalAccessException
    {
        String appid = p.getProperty(KEY_APPID);

        TokenProvider t = this.keeps.get(appid);
        if (t!=null)
            return(t);

        // else
        this.keep(
            t = this.instantiate(p)
        );

        return(t);
    }

    /** Get registered TokenProvider.
     */
    public TokenProvider get(String appid)
    {
        return(
            this.keeps.get(appid)
        );
    }

}
