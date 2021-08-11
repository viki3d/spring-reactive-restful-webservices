package com.viki3d.spring.reactive.restful.webservices.services.mock;

import com.viki3d.spring.reactive.restful.webservices.front.api.CarsApi;
import com.viki3d.spring.reactive.restful.webservices.front.api.model.Car;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}
 */
@Service
public class CarsMockService implements CarsApi {

  private static List<Car> mockDb = Arrays.asList(
    new Car(1L, "Mazda", "6", "red"),
    new Car(2L, "Mazda", "3", "green"),
    new Car(3L, "Infinity", "Q50", "gray"),
    new Car(4L, "Infinity", "Q60", "black")
  );

  @Override
  public List<Car> list() {
    return mockDb;
  }

  @Override
  public Optional<Car> get(long id) {
    Optional<Car> optonalCar;
    
    try {
      optonalCar = Optional.of(mockDb.get(((int) id) - 1));
    } catch (IndexOutOfBoundsException ex) {
      optonalCar = Optional.ofNullable(null);
    }

    return optonalCar;
  }

}
