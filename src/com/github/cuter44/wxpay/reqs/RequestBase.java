package com.github.cuter44.wxpay.reqs;

import java.util.Properties;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.github.cuter44.nyafx.crypto.*;
import com.github.cuter44.nyafx.text.*;
import org.apache.http.client.fluent.*;
import org.apache.http.entity.ContentType;

import com.github.cuter44.wxpay.WxpayException;
import com.github.cuter44.wxpay.WxpayProtocolException;
import com.github.cuter44.wxpay.resps.ResponseBase;
import static com.github.cuter44.wxpay.util.XMLParser.parseXML;

/**
 * @author galin<cuter44@foxmail.com>
 * @date 2014/6/18
 */
public abstract class RequestBase
{
    protected static final String KEY_APPID         = "appid";
    protected static final String KEY_SIGN          = "sign";
    protected static final String KEY_KEY           = "KEY";
    protected static final String KEY_RETURN_CODE   = "return_code";
    protected static final String KEY_RETURN_MSG    = "return_msg";
    protected static final String KEY_ERR_CODE      = "err_code";
    protected static final String KEY_ERR_CODE_DES  = "err_code_des";
    protected static final String KEY_NOTIFY_URL    = "notify_url";
    protected static final String KEY_NONCE_STR     = "nonce_str";

    protected static final String VALUE_SUCCESS = "SUCCESS";
    protected static final String VALUE_FAIL    = "FAIL";

    protected static CryptoBase crypto = CryptoBase.getInstance();

    protected static final int NONCE_STR_BYTES = 8;

  // CONSTRUCT
    public RequestBase(Properties aConf)
    {
        this.conf = aConf;
        this.setNonceStr(
            crypto.byteToHex(crypto.randomBytes(NONCE_STR_BYTES))
        );

        return;
    }

  // CONFIG
    protected Properties conf;

    public String getProperty(String key)
    {
        return(
            this.conf.getProperty(key)
        );
    }

    /**
     * chain supported
     */
    public RequestBase setProperty(String key, String value)
    {
        this.conf.setProperty(key, value);
        return(this);
    }

    //public RequestBase setCDATAProperty(String key, String value)
    //{
        //this.conf.setProperty(key, "<![CDATA["+value+"]]>");
        //return(this);
    //}

    /**
     * batch setProperty
     * @param aConf a Map contains key-value pairs, where key must be String, and values must implement toString() at least.
     */
    public RequestBase setProperties(Map aConf)
    {
        this.conf.putAll(aConf);
        return(this);
    }

  // BUILD
   public abstract RequestBase build();

  // SIGN
    /** sign
     * @exception UnsupportedOperationException if <code>sign_type</code> is other than <code>MD5</code>
     * @exception IllegalStateException if <code>Key</code> or something else (related to algorithm) not found
     */
    public abstract RequestBase sign()
        throws UnsupportedEncodingException, UnsupportedOperationException, IllegalStateException;

    /**
     * @param paramNames key names to submit, in dictionary order
     */
    protected String sign(List<String> paramNames)
        throws UnsupportedEncodingException, UnsupportedOperationException, IllegalStateException
    {
        String key = this.getProperty(KEY_KEY);

        String sign = this.signMD5(paramNames, key);

        //this.setCDATAProperty(KEY_SIGN, sign);
        this.setProperty(KEY_SIGN, sign);

        return(sign);
    }

    /**
     * caculate sign according to wxp-spec
     * @exception UnsupportedEncodingException if your runtime does not support utf-8.
     */
    protected String signMD5(List<String> paramNames, String key)
        throws UnsupportedEncodingException
    {
        if (key == null)
            throw(new IllegalArgumentException("No KEY, no sign. Please check your configuration."));

        StringBuilder sb = new StringBuilder()
            .append(this.toQueryString(paramNames))
            .append("&key="+key);

        String sign = this.crypto.byteToHex(
            this.crypto.MD5Digest(
                sb.toString().getBytes("utf-8")
        ));

        sign = sign.toUpperCase();

        return(sign);
    }

  // TO_URL
    /** Extract URL to execute request on client
     */
    public abstract String toURL()
        throws UnsupportedOperationException;

    /** Provide query string to sign().
     * toURL() may not invoke this method.
     */
    protected String toQueryString(List<String> paramNames)
    {
        URLBuilder ub = new URLBuilder();

        for (String key:paramNames)
            ub.appendParam(key, this.getProperty(key));

        return(ub.toString());
    }

  // TO_XML
    protected String toXml(List<String> paramNames)
    {
        StringBuilder xml = new StringBuilder();

        xml.append("<xml>");

        for (String k:paramNames)
        {
            String v = this.getProperty(k);
            if (v != null)
                xml.append('<').append(k).append('>')
                   .append(v)
                   .append("</").append(k).append('>');
        }

        xml.append('<').append(KEY_SIGN).append('>')
           .append(this.getProperty(KEY_SIGN))
           .append("</").append(KEY_SIGN).append('>');

        xml.append("</xml>");

        return(xml.toString());
    }


  // EXECUTE
    /** Execute the constructed query
     */
    public abstract ResponseBase execute()
        throws WxpayException, UnsupportedOperationException;
;

    public ResponseBase execute(String urlBase, List<String> paramNames)
        throws WxpayException
    {
        try
        {
            Properties prop = new Properties();

            String content = Request.Post(urlBase)
                .bodyString(
                    this.toXml(paramNames),
                    ContentType.create("text/xml", "utf-8")
                )
                .execute()
                .returnContent()
                .asString();

            prop.putAll(parseXML(content));

            if (VALUE_FAIL.equals(prop.getProperty(KEY_RETURN_CODE)))
                throw(
                    new WxpayProtocolException(
                        prop.getProperty(KEY_RETURN_MSG)
                ));

            if (VALUE_FAIL.equals(prop.getProperty(KEY_ERR_CODE)))
                throw(
                    new WxpayException(
                        prop.getProperty(KEY_ERR_CODE)
                ));

            return(new ResponseBase(content, prop));
        }
        catch (IOException ex)
        {
            throw(new WxpayException(ex));
        }
    }

  // MISC
    public RequestBase setAppid(String appid)
    {
        this.setProperty(KEY_APPID, appid);

        return(this);
    }

    public RequestBase setNonceStr(String nonceStr)
    {
        this.setProperty(KEY_NONCE_STR, nonceStr);

        return(this);
    }
}

