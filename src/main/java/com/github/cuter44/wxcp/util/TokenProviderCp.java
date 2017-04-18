package com.github.cuter44.wxcp.util;

import java.util.Properties;

public interface TokenProviderCp
{
    public abstract TokenProviderCp config(Properties p);
    public abstract String getCorpid();
    public abstract String getCorpsecret();

    public abstract TokenProviderCp forceRetrieveAccessToken();
    public abstract String getAccessToken();
    public abstract long getATExpire();

    public abstract TokenProviderCp forceRetrieveJsapiTicket();
    public abstract String getJsapiTicket();
    public abstract long getJTExpire();

    public abstract void close();
}
