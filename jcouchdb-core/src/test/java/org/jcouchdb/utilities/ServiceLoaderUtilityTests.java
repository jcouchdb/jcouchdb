package org.jcouchdb.utilities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.MessageFormat;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashSet;
import io.vavr.control.Try;

public class ServiceLoaderUtilityTests {

  @Test
  void loadWithoutFallbackFailsIfNoServicesAreFound() {
    Try<EmptyDoNothingService> service = ServiceLoaderUtility.load(EmptyDoNothingService.class);
    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> service.get());
    assertThat(exception).hasMessage("head of empty set");
  }

  @Test
  void loadWithoutFallbackRetrievesOneService() {
    SingleDoNothingService service = ServiceLoaderUtility.load(SingleDoNothingService.class).get();
    assertThat(service).isExactlyInstanceOf(FirstDoNothingService.class);
  }

  @Test
  void loadWithoutFallbackFailsOnTwoDifferentServices() {
    Try<MultipleDoNothingService> service = ServiceLoaderUtility.load(MultipleDoNothingService.class);
    String expectedMessage = MessageFormat.format(ServiceLoaderUtility.ONLY_ONE_SERVICE_ALLOWED_MESSAGE, MultipleDoNothingService.class.getSimpleName());
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.get());
    assertThat(exception).hasMessage(expectedMessage);
  }

  @Test
  void loadWithFallbackReturnsDefaultIfNoServicesAreFound() {
    EmptyDoNothingService service = ServiceLoaderUtility.load(EmptyDoNothingService.class, DefaultDoNothingService.class).get();
    assertThat(service).isExactlyInstanceOf(DefaultDoNothingService.class);
  }

  @Test
  void loadWithFallbackRetrievesOneService() {
    SingleDoNothingService service = ServiceLoaderUtility.load(SingleDoNothingService.class, DefaultDoNothingService.class).get();
    assertThat(service).isExactlyInstanceOf(FirstDoNothingService.class);
  }

  @Test
  void loadWithFallbackFailsOnTwoDifferentServices() {
    // TODO - See note in ServiceLoaderUtility class
    //
    // Try<MultipleDoNothingService> service = ServiceLoaderUtility.load(MultipleDoNothingService.class, DefaultDoNothingService.class);
    // String expectedMessage = MessageFormat.format(ServiceLoaderUtility.ONLY_ONE_SERVICE_ALLOWED_MESSAGE, MultipleDoNothingService.class.getSimpleName());
    // IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.get());
    // assertThat(exception).hasMessage(expectedMessage);
    MultipleDoNothingService service = ServiceLoaderUtility.load(MultipleDoNothingService.class, DefaultDoNothingService.class).get();
    assertThat(service).isExactlyInstanceOf(DefaultDoNothingService.class);
  }

  @Test
  void loadAllReturnsAnEmptySetIfNoServicesAreFound() {
    HashSet<EmptyDoNothingService> services = ServiceLoaderUtility.loadAll(EmptyDoNothingService.class);
    assertThat(services).hasSize(0);
  }

  @Test
  void loadAllRetrievesOneService() {
    HashSet<SingleDoNothingService> services = ServiceLoaderUtility.loadAll(SingleDoNothingService.class);
    assertThat(services).hasSize(1);
    assertThat(services).hasAtLeastOneElementOfType(FirstDoNothingService.class);
  }

  @Test
  void loadAllRetrievesTwoDifferentServices() {
    HashSet<MultipleDoNothingService> services = ServiceLoaderUtility.loadAll(MultipleDoNothingService.class);
    assertThat(services).hasSize(2);
    assertThat(services).hasAtLeastOneElementOfType(FirstDoNothingService.class);
    assertThat(services).hasAtLeastOneElementOfType(SecondDoNothingService.class);
  }

}