package com.github.cuter44.wxmsg.constants;

public enum MsgType
{
    text(),
    image(),
    voice(),
    video(),
    shortvideo(),
    location(),
    link();

    private MsgType()
    {
        return;
    }


}
