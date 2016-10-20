package com.github.cuter44.wxmp.resps;

import java.net.URL;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.WxmpException;

/** 查询所有分组
 * <pre style="font-size:12px">
    返回说明
    <del>groups  公众平台分组信息列表</del>
    id      分组id，由微信分配
    name    分组名字，UTF8编码
    count   分组内用户数量
 * </pre>
 */
public class GroupsGetResponse extends WxmpResponseBase
    implements Iterable<GroupsGetResponse.Group>
{
  // CONSTANTS
    public static final String KEY_ID   = "id";
    public static final String KEY_NAME = "name";

  // CONSTRUCT
    protected JSONArray groups;

    public GroupsGetResponse(String jsonString)
    {
        super(jsonString);

        this.groups = json.getJSONArray("groups");

        return;
    }

  // ACCESSOR
    /** @deprecated GroupsGetResponse does not support this op.
     */
    @Override
    public JSONObject getJson()
        throws UnsupportedOperationException
    {
        throw(new UnsupportedOperationException());
    }

    public static class Group
    {
        public int id;
        public String name;
        public long count;

        protected Group(JSONObject json)
        {
            this.id     = json.getInteger("id");
            this.name   = json.getString("name");
            this.count  = json.getLong("count");
        }
    }

    public class GroupsIterator
        implements Iterator<Group>
    {
        Iterator<Object> i;

        protected GroupsIterator()
        {
            this.i = GroupsGetResponse.this.groups.iterator();

            return;
        }

        public boolean hasNext()
        {
            return(
                this.i.hasNext()
            );
        }

        public Group next()
            throws NoSuchElementException
        {
            JSONObject j = (JSONObject)this.i.next();
            return(
                new Group(j)
            );
        }

        public void remove()
        {
            throw(new UnsupportedOperationException());
        }
    }

    public GroupsIterator iterator()
    {
        return(
            new GroupsIterator()
        );
    }
}
