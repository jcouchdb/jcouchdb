package org.jcouchdb.service;

import java.net.URL;

import org.jcouchdb.utilities.ServiceLoaderUtility;

import io.vavr.control.Try;

public interface Connection {

  public static Connection connect(URL url) {
    ServiceLoaderUtility.load(Connection.class, JreConnection.class);
    return null;
  }

  <T> void delete(Class<T> clazz);

  <T> T get(Class<T> clazz);

  <T> void post(T document);

  <T> void put(T document);

}