package com.github.cuter44.wxmp.resps;

import java.net.URL;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.alibaba.fastjson.*;

import com.github.cuter44.wxmp.WxmpException;

/** 获取公众号已创建的标签
 * <pre style="font-size:12px">
    返回说明
    <del>tags  公众平台分组信息列表</del>
    id      分组id，由微信分配
    name    分组名字，UTF8编码
    count   分组内用户数量
 * </pre>
 */
public class TagsGetResponse extends WxmpResponseBase
    implements Iterable<TagsGetResponse.Tag>
{
  // CONSTANTS
    public static final String KEY_ID       = "id";
    public static final String KEY_NAME     = "name";
    public static final String KEY_COUNT    = "count";
    public static final String KEY_TAGS     = "tags";


  // CONSTRUCT
    protected JSONArray tags;

    public TagsGetResponse(String jsonString)
    {
        super(jsonString);

        this.tags = json.getJSONArray(KEY_TAGS);

        return;
    }

  // ACCESSOR
    /** @deprecated TagsGetResponse does not support this op.
     */
    @Override
    public JSONObject getJson()
        throws UnsupportedOperationException
    {
        throw(new UnsupportedOperationException());
    }

    public static class Tag
    {
        public int id;
        public String name;
        public long count;

        protected Tag(JSONObject json)
        {
            this.id     = json.getInteger   (TagsGetResponse.KEY_ID     );
            this.name   = json.getString    (TagsGetResponse.KEY_NAME   );
            this.count  = json.getLong      (TagsGetResponse.KEY_COUNT  );
        }
    }

    public class TagsIterator
        implements Iterator<Tag>
    {
        Iterator<Object> i;

        protected TagsIterator()
        {
            this.i = TagsGetResponse.this.tags.iterator();

            return;
        }

        public boolean hasNext()
        {
            return(
                this.i.hasNext()
            );
        }

        public Tag next()
            throws NoSuchElementException
        {
            JSONObject j = (JSONObject)this.i.next();
            return(
                new Tag(j)
            );
        }

        public void remove()
        {
            throw(new UnsupportedOperationException());
        }
    }

    public TagsIterator iterator()
    {
        return(
            new TagsIterator()
        );
    }
}
