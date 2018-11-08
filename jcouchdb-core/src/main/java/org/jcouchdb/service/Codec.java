package org.jcouchdb.service;

public interface Codec {

    <T> String marshall(T object);
    <T> T unmarshall(Class<T> clazz, String json);

}