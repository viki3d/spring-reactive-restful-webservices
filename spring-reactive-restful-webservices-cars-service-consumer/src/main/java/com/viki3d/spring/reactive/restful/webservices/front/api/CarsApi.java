package com.viki3d.spring.reactive.restful.webservices.front.api;

import com.viki3d.spring.reactive.restful.webservices.front.api.model.Car;
import java.util.List;
import java.util.Optional;

/**
 * All car operations defined here.
 *
 * @author Victor Kirov
 * @version 1 
 */
public interface CarsApi {

  List<Car> list();
  
  Optional<Car> get(long id);

}
