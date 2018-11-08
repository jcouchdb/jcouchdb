package org.jcouchdb.service;

public class JsonbCodec implements Codec {

    @Override
    public <T> String marshall(T object) {
        return null;
    }

    @Override
    public <T> T unmarshall(Class<T> clazz, String json) {
        return null;
    }

}