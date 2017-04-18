package com.github.cuter44.wxcp.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

/** Magazine to create/store and recall TokenProviderCp.
 */
public class ATMagCp
{
    public static final String KEY_CORPID = "corpid";
    public static final String KEY_AT_PROVIDER = "AT_PROVIDER_CP";
    public static final Class<ATCoreCp> AT_PROVIDER_DEFAULTS = ATCoreCp.class;

  // CONSTRUCT
    protected Map<String, TokenProviderCp> keeps;

    public ATMagCp()
    {
        this.keeps = new HashMap<String, TokenProviderCp>();

        return;
    }

  // DEFAULT INSTANCE
    private static class DefaultInstance
    {
        public static ATMagCp instance = new ATMagCp();
    }

    public static ATMagCp getDefaultInstance()
    {
        return(DefaultInstance.instance);
    }

  // CREATE
    /** Instantiate a TokenProviderCp, w/o register to or recall from keep.
     */
    @SuppressWarnings("unchecked")
    public TokenProviderCp instantiate(Properties p)
        throws InstantiationException, IllegalAccessException
    {
        Class<? extends TokenProviderCp> pclass = null;

        try
        {
            pclass = (Class<? extends TokenProviderCp>)Class.forName(p.getProperty(KEY_AT_PROVIDER));
        }
        catch (Exception ex)
        {
            pclass = AT_PROVIDER_DEFAULTS;
        }

        TokenProviderCp t = pclass.newInstance();
        t.config(p);

        return(t);
    }

  // KEEP
    /** Register to internal keep, so can recall with corpid.
     * if t is not exactly same(==) with a previously registered one, t tweak the old one,
     * and the old's close() is called.
     *
     * @reture t
     */
    public TokenProviderCp keep(TokenProviderCp t)
    {
        String corpid = t.getCorpid();
        TokenProviderCp t0 = this.keeps.get(corpid);

        if (t == t0)
            return(t);

        // else
        synchronized(this.keeps)
        {
            t0 = this.keeps.get(corpid);

            if (t0 != null)
                t0.close();

            this.keeps.put(corpid, t);
        }

        return(t);
    }

    /** Get registered TokenProviderCp, or instantiate a new one.
     */
    public TokenProviderCp getOrCreate(Properties p)
        throws InstantiationException, IllegalAccessException
    {
        String corpid = p.getProperty(KEY_CORPID);

        TokenProviderCp t = this.keeps.get(corpid);
        if (t!=null)
            return(t);

        // else
        this.keep(
            t = this.instantiate(p)
        );

        return(t);
    }

    /** Get registered TokenProviderCp.
     */
    public TokenProviderCp get(String corpid)
    {
        return(
            this.keeps.get(corpid)
        );
    }

}
