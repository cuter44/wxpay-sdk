package com.github.cuter44.wxpay.resps;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.github.cuter44.nyafx.crypto.*;
import com.github.cuter44.nyafx.text.*;

import com.github.cuter44.wxpay.WxpayException;
import com.github.cuter44.wxpay.WxpayProtocolException;
import com.github.cuter44.wxpay.util.*;

/** General response passed by req.execute() or alipay callback/redirect gateways
 * Actually reqs/gateways passed excatly response type, i.e. sub-class of this.
 * I'm recommending you to see the sub-class javadoc... to help you write elegant code.
 * Notice that downloadbill's response does not inherit this class. That's ass hole.
 */
public class WxpayResponseBase
{
  // CONSTANTS
    public static final String KEY_KEY              = "KEY";
    public static final String KEY_SIGN             = "sign";
    public static final String KEY_SKIP_VERIFY_SIGN = "SKIP_VERIFY_SIGN";

    public static final String KEY_RETURN_CODE  = "return_code";
    public static final String KEY_RETURN_MSG   = "return_msg";
    public static final String KEY_RESULT_CODE  = "result_code";
    public static final String KEY_ERR_CODE     = "err_code";
    public static final String KEY_ERR_CODE_DES = "err_code_des";

    public static final String VALUE_SUCCESS    = "SUCCESS";
    public static final String VALUE_FAIL       = "FAIL";

    protected static CryptoBase crypto = CryptoBase.getInstance();

    protected Boolean validity = null;

  // STRING
    /** @deprecated since 0.4.5 WxpayResponse no longer preserve the origin response body
     */
    protected String respString;
    /**
     * retrieve callback params or response content as String
     * @deprecated since 0.4.5 WxpayResponse no longer preserve the origin response body
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

    public final String getProperty(String key)
    {
        return(this.respProp.getProperty(key));
    }

    public final Integer getIntProperty(String key)
    {
        String v = this.getProperty(key);
        return(
            (v!=null) ? Integer.valueOf(v) : null
        );
    }

    public final Date getDateProperty(String key)
    {
        try
        {
            String v = this.getProperty(key);
            return(
                (v!=null) ? new SimpleDateFormat("yyyyMMddHHmmss").parse(v) : null
            );
        }
        catch (Exception ex)
        {
            // rarely occurs
            ex.printStackTrace();
            return(null);
        }
    }

  // CONSTRUCT
    public WxpayResponseBase()
    {
        return;
    }

    /**
     * This construstor automatically parse input as xml, and output properties. Meanwhile, detect the fails.
     * Notice that Properties does not support hierachy, so it go down if tag names are non-unique.
     * It is raw in present. If it really happens, a new response type and parser should be defined to cope with that.
     */
    public WxpayResponseBase(String xml)
        throws WxpayProtocolException, WxpayException
    {
        this.respString = xml;
        this.respProp = XMLParser.parseXML(xml);

        if (!this.isReturnCodeSuccess())
            throw(
                new WxpayProtocolException(
                    this.respProp.getProperty(KEY_RETURN_MSG)
            ));

        if (!this.isResultCodeSuccess())
            throw(
                new WxpayException(
                    this.respProp.getProperty(KEY_ERR_CODE)
            ));

        return;
    }

    /**
     * This construstor automatically parse input as xml, and output properties. Meanwhile, detect the fails.
     * Notice that Properties does not support hierachy, so it go down if tag names are non-unique.
     * It is raw in present. If it really happens, a new response type and parser should be defined to cope with that.
     */
    public WxpayResponseBase(InputStream xml)
        throws IOException
    {
        this.respProp = XMLParser.parseXML(xml);

        if (!this.isReturnCodeSuccess())
            throw(
                new WxpayProtocolException(
                    this.respProp.getProperty(KEY_RETURN_MSG)
            ));

        if (!this.isResultCodeSuccess())
            throw(
                new WxpayException(
                    this.respProp.getProperty(KEY_ERR_CODE)
            ));

        xml.close();

        return;
    }


    /** @deprecated due to it doesn't parse and detect fails. ONLY FOR DEBUGGING.
     */
    public WxpayResponseBase(Properties aRespProp)
    {
        this(null, aRespProp);

        return;
    }

    /** @deprecated due to it doesn't parse and detect fails. ONLY FOR DEBUGGING.
     */
    public WxpayResponseBase(String aRespString, Properties aRespProp)
    {
        this.respString = aRespString;
        this.respProp = aRespProp;

        return;
    }

  // EXCEPTION
    /** 此字段是通信标识，非交易标识，交易是否成功需要查看 result_code 来判断
     */
    public boolean isReturnCodeSuccess()
    {
        return(
            VALUE_SUCCESS.equals(this.getProperty(KEY_RETURN_CODE))
        );
    }

    public WxpayProtocolException getReturnMsg()
    {
        String returnMsg = this.getProperty(KEY_RETURN_MSG);

        if (returnMsg != null)
            return(new WxpayProtocolException(returnMsg));

        // else
        return(null);
    }

    public boolean isResultCodeSuccess()
    {
        return(
            VALUE_SUCCESS.equals(this.getProperty(KEY_RESULT_CODE))
        );
    }

    public WxpayException getErrCode()
    {
        String errCode = this.getProperty(KEY_ERR_CODE);

        if (errCode != null)
            return(new WxpayException(errCode));

        // else
        return(null);
    }

    public String getErrCodeDes()
    {
        return(
            this.getProperty(KEY_ERR_CODE_DES)
        );
    }

  // VERIFY
    /**
     * verify response sign
     * @return true if passed (i.e. response content should be trusted), otherwise false
     */
    public boolean verify(Properties conf)
        throws UnsupportedEncodingException
    {
        if (this.validity != null)
            return(this.validity);

        Boolean skipSign    = Boolean.valueOf(conf.getProperty("SKIP_VERIFY_SIGN"));

        // else
        this.validity = true;

        if (!skipSign)
            this.validity = this.validity && this.verifySign(conf);

        return(this.validity);
    }

    /** 子类应该实现这个方法以验证签名
     * SUB-CLASS MUST IMPLEMENT THIS METHOD TO BE CALLBACKED. Default behavior do no verification, i.e. always return true.
     * A typical implemention should be <code>return(super.verifySign(this.keysParamName, conf)</code> where
     *  );
     */
    protected boolean verifySign(Properties conf)
        throws UnsupportedEncodingException, UnsupportedOperationException
    {
        // DEFAULT IMPLEMENT
        return(true);
    }

    protected boolean verifySign(List<String> paramNames, Properties conf)
        throws UnsupportedEncodingException
    {
        this.respProp = buildConf(this.respProp, conf);
        String stated = this.getProperty("sign");
        String calculated = this.sign(
            paramNames
        );

        return(
            stated!=null && stated.equals(calculated)
        );
    }

    /**
     * @param paramNames key names to submit, in dictionary order
     */
    protected String sign(List<String> paramNames)
        throws UnsupportedEncodingException, UnsupportedOperationException, IllegalStateException
    {
        String key = this.getProperty(KEY_KEY);

        String sign = this.signMD5(paramNames, key);

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
            throw(new IllegalArgumentException("KEY required to sign, but not found."));

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


    /** Provide query string to sign().
     * toURL() may not invoke this method.
     */
    protected String toQueryString(List<String> paramNames)
    {
        URLBuilder ub = new URLBuilder();

        for (String key:paramNames)
        {
            if (KEY_SIGN.equals(key))
                continue;

            String value = this.getProperty(key);
            if (value == null || value.isEmpty())
                continue;

            ub.appendParam(key, value);
        }

        return(ub.toString());
    }

  // UTIL
    protected static Properties buildConf(Properties prop, Properties defaults)
    {
        Properties ret = new Properties(defaults);
        Iterator<String> iter = prop.stringPropertyNames().iterator();
        while (iter.hasNext())
        {
            String key = iter.next();
            ret.setProperty(key, prop.getProperty(key));
        }

        return(ret);
    }

    protected static String materializeParamName(String template, Integer ... params)
    {
        String s = template;

        for (int i=0; i<params.length; i++)
            s = s.replace("$"+i, Integer.toString(params[i]));

        return(s);
    }

    private static String[] internalMaterializeParamNames(String template, int level, Integer count0)
    {
        if (count0 == null || count0 == 0)
            return(new String[0]);

        if (!template.contains("$"))
            return(new String[]{template});

        String placeholder = "$"+level;

        String[] m0 = new String[count0];

        for (int i=0; i<count0; i++)
            m0[i] = template.replace(placeholder, Integer.toString(i));

        Arrays.sort(m0);

        return(m0);
    }

    protected static List<String> materializeParamNames(String template, int level, Integer count0)
    {
        if (count0 == null || count0 == 0)
            return(new ArrayList<String>());

        return(
            Arrays.asList(
                WxpayResponseBase.internalMaterializeParamNames(template, level, count0)
            )
        );
    }


    protected static List<String> materializeParamNames(String template, int level, Integer count0, List ... counts)
    {
        if (count0 == null || count0 == 0)
            return(new ArrayList<String>());

        String[] m0 = WxpayResponseBase.internalMaterializeParamNames(template, level, count0);
        List<String> mx;

        if (!m0[0].contains("$"))
        {
            mx = Arrays.asList(m0);
        }
        else
        {
            mx = new ArrayList<String>();

            List<Integer> count1s = (List<Integer>)counts[0];

            if (counts.length == 1)
            {
                for (int i=0; i<count0; i++)
                {
                    Integer count1 = count1s.get(i);
                    if (count1 == null)
                        continue;

                    mx.addAll(
                        materializeParamNames(m0[i], level+1, count1)
                    );
                }
            }
            else
            {
                for (int i=0; i<count0; i++)
                {
                    Integer count1 = count1s.get(i);
                    if (count1 == null)
                        continue;

                    List[] countsDesc = new List[counts.length-1];
                    for (int j=1; j<counts.length; j++)
                        countsDesc[j-1] = (List)counts[j].get(i);

                    mx.addAll(
                        materializeParamNames(m0[i], level+1, count1, countsDesc)
                    );
                }
            }
        }

        return(mx);
    }
}

