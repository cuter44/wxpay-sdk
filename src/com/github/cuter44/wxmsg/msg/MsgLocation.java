package com.github.cuter44.wxmsg.msg;

import java.util.Properties;
import java.awt.Point;

public class MsgLocation extends MsgBase
{
  // CONSTRUCT
    public MsgLocation()
    {
        super();

        return;
    }

    public MsgLocation(Properties prop)
    {
        super(prop);

        return;
    }

  // ACCESSOR
    public static final String KEY_LOCATION_X = "Location_X";
    public static final String KEY_LOCATION_Y = "Location_Y";
    public static final String KEY_SCALE = "Scale";
    public static final String KEY_LABEL = "Label";

    public Point getLocation()
    {
        Point p = new Point();
        p.setLocation(
            Double.valueOf(super.getProperty(KEY_LOCATION_X)),
            Double.valueOf(super.getProperty(KEY_LOCATION_Y))
        );

        return(p);
    }

    public int getScale()
    {
        return(
            Integer.valueOf(
                super.getProperty(KEY_SCALE)
            )
        );
    }

    public String getLabel()
    {
        return(
            super.getProperty(KEY_LABEL)
        );
    }
}
