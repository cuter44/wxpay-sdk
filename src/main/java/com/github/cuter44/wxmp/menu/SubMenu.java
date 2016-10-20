package com.github.cuter44.wxmp.menu;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.*;

/** Wxmp menu builder: top menu
 *
 * Please use only overrided methods, which verify constraints.
 */
public class SubMenu extends JSONObject
    implements TopMenuElement
{
    public static final int SUB_MENU_SIZE_CONSTRAINT = 5;
    public static final int ELEMENT_NAME_BYTE_SIZE_CONSTRAINT = 40;

    public static final String KEY_NAME = "name";
    public static final String KEY_SUB_BUTTON = "sub_button";

    protected JSONArray button;

    public SubMenu()
    {
        this.button = new JSONArray();
        this.put(KEY_SUB_BUTTON, this.button);

        return;
    }

    public SubMenu(String name, SubMenuElement ... elements)
    {
        this();

        this.setName(name);

        for (SubMenuElement e:elements)
            this.add(e);

        return;
    }

    public SubMenu add(SubMenuElement e)
    {
        if (this.size()>=SUB_MENU_SIZE_CONSTRAINT)
            throw(new ArrayIndexOutOfBoundsException("Sub menu element limit "+SUB_MENU_SIZE_CONSTRAINT+" excessed."));

        try
        {
            if (e.getName().getBytes("UTF-8").length > ELEMENT_NAME_BYTE_SIZE_CONSTRAINT)
                throw(new IllegalArgumentException("Element name length limit "+ELEMENT_NAME_BYTE_SIZE_CONSTRAINT+" bytes excessed."));
        }
        catch (UnsupportedEncodingException ex)
        {
            // never occur
            ex.printStackTrace();
        }

        this.button.add(e);

        return(this);
    }

    public SubMenu setName(String name)
    {
        this.put(KEY_NAME, name);

        return(this);
    }

    public String getName()
    {
        return(
            this.getString(KEY_NAME)
        );
    }

}
