package com.github.cuter44.wxmsg.msg;

import java.util.Properties;

public class EventLocation extends EventBase
{
  // CONSTRUCT
    public EventLocation()
    {
        super();

        return;
    }

    public EventLocation(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSORS
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";
    public static final String KEY_PRECISION = "Precision";

    public Double getLatitude()
    {
        return(
            super.getDoubleProperty(KEY_LATITUDE)
        );
    }

    public Double getLongitude()
    {
        return(
            super.getDoubleProperty(KEY_LONGITUDE)
        );
    }

    public Double getPrecision()
    {
        return(
            super.getDoubleProperty(KEY_PRECISION)
        );
    }
}
