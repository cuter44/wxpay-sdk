package com.github.cuter44.wxmp.constants;

public enum MsgSendType
{
    text(),
    image(),
    voice(),
    video(),
    music(),
    news(),
    wxcard(),
    UNKNOWN();

    /** 从名字实例化.
     * Type-safe for unknown type
     */
    public static MsgSendType forName(String name)
    {
        try
        {
            return(
                MsgSendType.valueOf(name)
            );
        }
        catch (IllegalArgumentException ex)
        {
            return(MsgSendType.UNKNOWN);
        }
    }

}
