package org.jcouchdb.utilities;

import java.text.MessageFormat;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import io.vavr.collection.HashSet;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceLoaderUtility {

  public static final String ONLY_ONE_SERVICE_ALLOWED_MESSAGE = "Only a single service of type {0} is allowed";

  public static <T> Try<T> load(Class<T> clazz) {
    HashSet<T> services = loadAll(clazz);
    if (services.size() > 1) {
      String message = MessageFormat.format(ONLY_ONE_SERVICE_ALLOWED_MESSAGE, clazz.getSimpleName());
      return Try.failure(new IllegalStateException(message));
    }
    return services.toTry();
  }

  // TODO - this currently returns the default when two services are present
  //        but is that the correct behavior or should it throw an
  //        IllegalStateException like load(Class) does?
  public static <T> Try<T> load(Class<T> clazz, Class<? extends T> fallbackClazz) {
    return load(clazz).orElse(Try.of(() -> fallbackClazz.newInstance()));
  }

  public static <T> HashSet<T> loadAll(Class<T> clazz) {
    return HashSet.ofAll(StreamSupport.stream(ServiceLoader.load(clazz).spliterator(), false));
  }

}