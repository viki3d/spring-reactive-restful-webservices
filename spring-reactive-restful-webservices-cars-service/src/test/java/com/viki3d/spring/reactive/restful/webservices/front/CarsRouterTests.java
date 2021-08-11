package com.viki3d.spring.reactive.restful.webservices.front;

import static org.assertj.core.api.Assertions.assertThat;

import com.viki3d.spring.reactive.restful.webservices.front.api.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests for Cars API serving.
 */
@SpringBootTest
public class CarsRouterTests {

  private WebTestClient webTestClient;

  @BeforeEach
  void setUp(ApplicationContext context) {  
    webTestClient = WebTestClient.bindToApplicationContext(context).build(); 
  }

  @Test
  public void testGetCar1() {
    webTestClient
        .get().uri("/api/v1/cars/1")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Car.class).value(car -> {
          assertThat(car.getId()).isEqualTo(1L);
        });
  }
  
  @Test
  public void testGetCar2() {
    webTestClient
        .get().uri("/api/v1/cars/2")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Car.class).value(car -> {
          assertThat(car.getId()).isEqualTo(2L);
        });
  }

  @Test
  public void testGetCars() {
    webTestClient
        .get().uri("/api/v1/cars")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Car.class).hasSize(4);
  }

}