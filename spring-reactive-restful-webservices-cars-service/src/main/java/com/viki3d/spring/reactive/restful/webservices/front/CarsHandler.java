package com.viki3d.spring.reactive.restful.webservices.front;

import com.viki3d.spring.reactive.restful.webservices.front.api.CarsApi;
import com.viki3d.spring.reactive.restful.webservices.front.api.model.Car;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Reactive handled requests. 
 */
@Component
public class CarsHandler {

  @Autowired
  private CarsApi carsMockService;

  public Mono<ServerResponse> list(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(carsMockService.list()));
  }

  /**
   * Get car by id.
   *
   * @param request The web request.
   * @return The Car object HTTP-200 or HTTP-404 if car not found.
   */
  public Mono<ServerResponse> get(ServerRequest request) {
    Mono<ServerResponse> serverResponse;
    long carId = Long.parseLong(request.pathVariable("id"));

    Optional<Car> car = carsMockService.get(carId);
    if (car.isEmpty()) {
      serverResponse = ServerResponse.notFound().build();
    } else {
      serverResponse = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(car.get()));
    }
    
    return serverResponse;
  }
  
}
