package com.viki3d.spring.reactive.restful.webservices.front.controllers;

import com.viki3d.spring.reactive.restful.webservices.front.api.model.Car;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.Consumer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The main controller, used for demonstration of Reactive calls.
 */
@Controller
@RequestMapping("/")
public class IndexController {

  // Cached values of the Asynch calls.
  // We will provide these values to interface later.
  private static Car cachedCar;
  private static Long cachedDurationInMilliseconds = null;

  private static final boolean CALL_MODE_ASYNCHRONOUS = true;
  private static final String BASE_URL = "http://localhost:8080";
  private static final String ATTR_CAR = "car";
  private static final String ATTR_CARS = "cars";
  private static final String ATTR_CAR_ID = "carId";
  private static final String ATTR_CALL_DURATION = "callDuration";

  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  /**
   * The main interface for demonstrating the Reactive calls. Pick up from here a Reactive endpoint 
   * to demonstrate.
   *
   * @return The page body. We intentionally do not use Template engine (like Thymeleaf) to make the
   *     code logic simpler.
   */
  @GetMapping("/")
  public String index(Model model) {
    return "index.html";
  }

  /**
   * Call this controller endpoint to see demonstration of reactive calls.<br/>
   * /reactcall/cars/get/{id} -&gt; /api/v1/cars/{id}
   *
   * @param id The id of the car you want to retrieve.
   * @return   The status of Reactive operations. We intentionally do not use Template engine (like 
   *     Thymeleaf) to make the code logic simpler.
   */
  @GetMapping("/reactcall/cars/{id}")
  public String reactGetCar(@PathVariable long id, Model model) {
    WebClient.Builder builder = WebClient.builder();
    WebClient webClient = builder.build();

    Mono<Car> monoCar = webClient
        .get()
        .uri(BASE_URL + "/api/v1/cars/" + id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve().bodyToMono(Car.class);

    Car car = null;
    LocalTime startTime = LocalTime.now();
    LocalTime finalTime;
    Long durationInMilliseconds = null;

    if (!CALL_MODE_ASYNCHRONOUS) {
      // Wait(block) until car is retrieved (SYNCH)
      car = monoCar.block();
      finalTime = LocalTime.now();
      durationInMilliseconds = ChronoUnit.MILLIS.between(startTime, finalTime);
      logger.debug("! SYNCH");
      logger.debug("! milliseconds = " + durationInMilliseconds);
      logger.debug("! car = " + car.toString());
    } else {
      // Receive car in this consumer when it is retrieved. (ASYNCH)
      Consumer<Car> carConsumer = (Car c) -> {
        IndexController.cachedCar = null;
        IndexController.cachedDurationInMilliseconds = null;
        LocalTime localFinalTime = LocalTime.now();
        long localDurationInMilliseconds = ChronoUnit.MILLIS.between(startTime, localFinalTime);
        logger.debug("! ASYNCH");
        logger.debug("! milliseconds = " + localDurationInMilliseconds);
        logger.debug("! car = " + c.toString());
        IndexController.cachedCar = c;
        IndexController.cachedDurationInMilliseconds = localDurationInMilliseconds;
      };
      Consumer<Throwable> errorConsumer = (Throwable t) -> {
        logger.debug("! ERROR");
        logger.debug("! " + t.getMessage());
        IndexController.cachedCar = new Car();
        IndexController.cachedDurationInMilliseconds = -1L;
      };
      monoCar.subscribe(carConsumer, errorConsumer);
    }

    model.addAttribute(ATTR_CAR_ID, id);
    model.addAttribute(ATTR_CAR, car);
    model.addAttribute(ATTR_CALL_DURATION, durationInMilliseconds);
    return "car.html";
  }
  
  /**
   * Provides the cached result of the asynchronous call later to interface.
   *
   * @return JSON, containing the cached result. 
   */
  @GetMapping("/getcache/car")
  public @ResponseBody String getCarCallCache() {
    JSONObject jo = new JSONObject();
    jo.put(ATTR_CAR, IndexController.cachedCar);
    jo.put(ATTR_CALL_DURATION, IndexController.cachedDurationInMilliseconds);
    return jo.toString();
  }

  /**
   * Call this controller endpoint to see demonstration of reactive calls.<br/>
   * /react/cars -&gt; /api/v1/cars
   *
   * @return   The status of Reactive operations. We intentionally do not use Template engine (like 
   *     Thymeleaf) to make the code logic simpler.
   */
  @GetMapping("/reactcall/cars")
  public String reactListCars(Model model) {
    WebClient.Builder builder = WebClient.builder();
    WebClient webClient = builder.build();

    Mono<Car[]> monoCars = webClient
        .get()
        .uri(BASE_URL + "/api/v1/cars")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve().bodyToMono(Car[].class);

    LocalTime startTime = LocalTime.now();
    LocalTime finalTime;
    Long durationInMilliseconds = null;

    Car[] cars = new Car[] {};
    
    if (!CALL_MODE_ASYNCHRONOUS) {
      // Wait(block) until cars array is retrieved (SYNCH)
      cars = monoCars.block();
      finalTime = LocalTime.now();
      durationInMilliseconds = ChronoUnit.MILLIS.between(startTime, finalTime);
      logger.debug("! milliseconds = " + durationInMilliseconds);
      Arrays.stream(cars).forEach(car -> logger.debug("! " + car.toString()));
    } else {
      // Subscribe to receive cars array when it is retrieved. (ASYNCH)
      Consumer<Car[]> carConsumer = (Car[] carsArray) -> {
        LocalTime localFinalTime = LocalTime.now();
        long localDurationInMilliseconds = ChronoUnit.MILLIS.between(startTime, localFinalTime);
        logger.debug("! milliseconds = " + localDurationInMilliseconds);
        Arrays.stream(carsArray).forEach(car -> logger.debug("! " + car.toString()));
      };
      monoCars.subscribe(carConsumer);
    }
    
    model.addAttribute(ATTR_CARS, cars);
    model.addAttribute(ATTR_CALL_DURATION, durationInMilliseconds);
    return "cars.html";
  }

}
