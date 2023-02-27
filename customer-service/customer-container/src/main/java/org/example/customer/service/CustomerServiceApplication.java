package org.example.customer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"org.example.customer.service.dataaccess", "org.example.dataaccess"})
@EntityScan(basePackages = {"org.example.customer.service.dataaccess", "org.example.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.example")
public class CustomerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}
