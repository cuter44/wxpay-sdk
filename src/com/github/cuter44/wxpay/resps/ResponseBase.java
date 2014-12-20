package com.github.cuter44.wxpay.resps;

import java.util.Properties;

/** General response passed by req.execute() or alipay callback/redirect gateways
 * Actually reqs/gateways passed excatly response type, i.e. sub-class of this.
 * I'm recommending you to see the sub-class javadoc... to help you write elegant code.
 */
public class ResponseBase
{
  // STRING
    protected String respString;
    /**
     * retrieve callback params or response content as String
     */
    public String getString()
    {
        return(this.respString);
    }

  // PROPERTIES
    protected Properties respProp;

    /**
     * retrieve callback params or response content as Properties
     */
    public Properties getProperties()
    {
        return(this.respProp);
    }

  // CONSTRUCT
    public ResponseBase()
    {
        return;
    }

    public ResponseBase(String aRespString)
    {
        this(aRespString, null);

        return;
    }

    public ResponseBase(Properties aRespProp)
    {
        this(null, aRespProp);

        return;
    }

    public ResponseBase(String aRespString, Properties aRespProp)
    {
        this.respString = aRespString;
        this.respProp = aRespProp;

        return;
    }
}
