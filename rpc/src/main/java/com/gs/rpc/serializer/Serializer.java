package com.gs.rpc.serializer;

public interface Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);

}
