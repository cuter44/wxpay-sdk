package com.github.cuter44.wxmp.resps;

import java.net.URL;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.WxmpException;

/** 获取标签下粉丝列表
 * <pre style="font-size:12px">
    返回说明
    count       这次获取的粉丝数量
    <del>data        公众平台分组信息列表</del>
    openid
    next_openid 拉取列表最后一个用户的openid
 * </pre>
 */
public class UserTagGetResponse extends WxmpResponseBase
    implements Iterable<String>
{
  // CONSTANTS
    public static final String KEY_COUNT        = "count";
    public static final String KEY_DATA         = "data";
    public static final String KEY_OPENID       = "openid";
    public static final String KEY_NEXT_OPENID  = "next_openid";


  // CONSTRUCT
    protected JSONArray openids;

    public UserTagGetResponse(String jsonString)
    {
        super(jsonString);

        this.openids = json.getJSONArray(KEY_OPENID);

        return;
    }

  // ACCESSOR
    public class OpenidIterator
        implements Iterator<String>
    {
        int i;

        protected OpenidIterator()
        {
            this.i = 0;

            return;
        }

        @Override
        public boolean hasNext()
        {
            return(
                this.i < UserTagGetResponse.this.openids.size()
            );
        }

        @Override
        public String next()
            throws NoSuchElementException
        {
            return(
                UserTagGetResponse.this.openids.getString(this.i++)
            );
        }

        @Override
        public void remove()
        {
            throw(new UnsupportedOperationException());
        }
    }

    @Override
    public OpenidIterator iterator()
    {
        return(
            new OpenidIterator()
        );
    }

    public boolean hasNextOpenid()
    {
        return(this.json.containsKey(KEY_NEXT_OPENID));
    }

    public String getNextOpenid()
    {
        return(super.getString(KEY_NEXT_OPENID));
    }

    public Integer getCount()
    {
        return(super.getInteger(KEY_COUNT));
    }

    public JSONArray getData()
    {
        return(this.json.getJSONArray(KEY_DATA));
    }
}
