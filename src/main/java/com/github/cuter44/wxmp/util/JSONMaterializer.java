package com.github.cuter44.wxmp.util;

import java.util.Properties;
import java.util.Hashtable;

import com.alibaba.fastjson.*;
import com.github.cuter44.nyafx.servlet.ValueParser;
import static com.github.cuter44.nyafx.servlet.PrimitiveParsers.*;

/** A lite json schema materializer implemention for building wxmp/wxmp reqs' json body.
 * @since 0.7.0.0
 */
public class JSONMaterializer
{
  // CONSTRUCT
    public Hashtable<String, ValueParser> parsers;

    public JSONMaterializer()
    {
        this.parsers = new Hashtable<String, ValueParser>();

        this.parsers.put("string", StringParser.instance);
        this.parsers.put("integer", LongParser.instance);
        this.parsers.put("number", DoubleParser.instance);

        return;
    }

  // DEFAULT INSTANCE
    public static JSONMaterializer instance = new JSONMaterializer();

  // MATERIALIZE
    public JSONObject materialize(JSONObject jsonSchema, Properties p)
    {
        JSONObject j = new JSONObject();

        JSONObject nodes = jsonSchema.getJSONObject("properties");

        for (String k:nodes.keySet())
        {
            JSONObject node = nodes.getJSONObject(k);

            String t = node.getString("type");
            Object v = null;

            if ("object".equals(t))
            {
                v = this.materialize(node.getJSONObject("schema"), p);
            }
            else
            {
                v = this.parsers.get(t)
                    .parseString(
                        p.getProperty(k), null
                    );
            }

            // label put:
            j.put(k, v);
        }

        System.out.println(j);

        return(j);
    }


    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}
