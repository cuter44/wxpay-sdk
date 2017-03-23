package com.github.cuter44.wxmp.util;

import java.util.Properties;

public interface TokenProvider
{
    public abstract TokenProvider config(Properties p);
    public abstract String getAppid();
    public abstract String getSecret();

    public abstract TokenProvider forceRetrieveAccessToken();
    public abstract String getAccessToken();
    public abstract long getATExpire();

    public abstract TokenProvider forceRetrieveJSSDKTicket();
    public abstract String getJSSDKTicket();
    public abstract long getJTExpire();

    public abstract void close();
}
