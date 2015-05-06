package com.github.cuter44.wxmp.sample;

import com.github.cuter44.wxmp.*;
import com.github.cuter44.wxmp.reqs.*;
import com.github.cuter44.wxmp.resps.*;
import com.github.cuter44.wxmp.util.*;

public class SampleTokenClientCredential
{
    public static void main(String[] args)
    {
        try
        {
            WxmpFactory factory = WxmpFactory.getDefaultInstance();

            TokenClientCredential req = factory.newTokenClientCredential();

            TokenClientCredentialResponse resp = req.build().execute();

            System.out.println(resp.json);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
