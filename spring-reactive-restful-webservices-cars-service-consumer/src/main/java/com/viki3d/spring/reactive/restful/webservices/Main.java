package com.viki3d.spring.reactive.restful.webservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The default SpringBoot configuration class.
 */
@SpringBootApplication
public class Main {

  private final static Logger logger = LoggerFactory.getLogger(Main.class);
	
  /**
   * The entry point of this SpringBoot application.
   *
   * @param args The command line parameters passed to this application. Currently no such 
   *     parameters are supported by this application.
   */
  public static void main(String[] args) {

    // Get the Spring Context
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

    // Ensures a graceful shutdown and calls the relevant destroy methods on your singleton 
    // beans so that all resources are released.
    context.registerShutdownHook();

    logger.debug("! Reactive CarsService CONSUMER");
  }

}
