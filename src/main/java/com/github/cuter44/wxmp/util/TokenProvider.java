package com.github.cuter44.wxmp.util;

public interface TokenProvider
{
    public abstract TokenProvider config(String appid, String secret);
    public abstract TokenProvider forceRetrieveAccessToken();
    public abstract String getAccessToken();
    public abstract TokenProvider forceRetrieveJSSDKTicket();
    public abstract String getJSSDKTicket();
}
