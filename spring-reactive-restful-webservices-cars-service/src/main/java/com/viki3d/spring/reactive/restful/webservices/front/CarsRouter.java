package com.viki3d.spring.reactive.restful.webservices.front;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * The Reactive Router configuration here.
 *
 * @author Victor Kirov
 */
@Configuration(proxyBeanMethods = false)
public class CarsRouter {

  /**
   * Defining application's served endpoints.
   */
  @Bean
  public RouterFunction<ServerResponse> route(CarsHandler carsHandler) {
    return RouterFunctions
      .route(GET("/api/v1/cars").and(accept(MediaType.APPLICATION_JSON)), carsHandler::list)
      .andRoute(GET("/api/v1/cars/{id:[0-9]+}")
          .and(accept(MediaType.APPLICATION_JSON)), carsHandler::get);
  }
  
}