package com.github.cuter44.wxpay.util;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.lang.ThreadLocal;

public class CSTConvert
{
    public static final TimeZone TZ = TimeZone.getTimeZone("Etc/GMT-8");
    public static final String PATTERN = "yyyyMMddHHmmss";

    public static class SDFThreadLocal extends ThreadLocal<SimpleDateFormat>
    {
        @Override
        protected SimpleDateFormat initialValue()
        {
            SimpleDateFormat df = new SimpleDateFormat(CSTConvert.PATTERN);
            df.setTimeZone(TZ);

            return(df);
        }
    }

    public static final SDFThreadLocal SDF = new SDFThreadLocal();

    /** Parsing datetime according wxpay convention.
     */
    public static Date parse(String yyyyMMddHHmmss)
        throws ParseException
    {
        return(
            SDF.get().parse(yyyyMMddHHmmss)
        );
    }

    /** Formating datetime according wxpay convention.
     */
    public static String format(Date utc)
    {
        return(
            SDF.get().format(utc)
        );
    }
}
