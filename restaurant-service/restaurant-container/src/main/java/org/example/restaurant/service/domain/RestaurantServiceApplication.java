package org.example.restaurant.service.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "org.example.restaurant.service.dataaccess", "org.example.dataaccess" })
@EntityScan(basePackages = { "org.example.restaurant.service.dataaccess", "org.example.dataaccess" })
@SpringBootApplication(scanBasePackages = "org.example")
public class RestaurantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
}
