package com.home.eshopee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring Boot based monolithic application.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@EnableAsync
public class EShopeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EShopeeApplication.class, args);
    }
}
