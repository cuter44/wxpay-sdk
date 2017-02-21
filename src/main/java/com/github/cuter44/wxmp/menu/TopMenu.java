package com.github.cuter44.wxmp.menu;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.*;

/** Wxmp menu builder: top menu
 *
 * Please use only overrided methods, which verify constraints.
 */
public class TopMenu extends JSONArray
{
    public static final int TOP_MENU_SIZE_CONSTRAINT = 3;
    public static final int ELEMENT_NAME_BYTE_SIZE_CONSTRAINT = 16;

    public static final String KEY_NAME = "name";

  // CONSTRUCT
    public TopMenu()
    {
        super();
    }

    public TopMenu(TopMenuElement ... elements)
    {
        this();

        for (TopMenuElement e:elements)
            this.add(e);

        return;
    }


    /** @param e must have name set before add.
     */
    public TopMenu add(TopMenuElement e)
        throws ArrayIndexOutOfBoundsException, IllegalArgumentException
    {
        if (this.size() >= TOP_MENU_SIZE_CONSTRAINT)
            throw(new ArrayIndexOutOfBoundsException("Top menu element limit "+TOP_MENU_SIZE_CONSTRAINT+" excessed."));

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

        return(this);
    }

    @Override
    @Deprecated
    public boolean add(Object e)
    {
        return(
            super.add(e)
        );
    }

    @Override
    @Deprecated
    public void add(int index, Object element)
    {
        super.add(index, element);

        return;
    }

}
