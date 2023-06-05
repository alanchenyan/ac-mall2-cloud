package com.ac.common.base.mongo;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.lang.reflect.Type;

public class ObjectIdSerializer implements ObjectSerializer, ObjectDeserializer {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return (T) new ObjectId(parser.parseObject(String.class));
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        ObjectId id = (ObjectId) object;
        serializer.write(id.toString());
    }
}
