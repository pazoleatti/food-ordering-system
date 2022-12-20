package org.example.order.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "org.example.order.service.dataaccess" })
@EntityScan(basePackages = { "org.example.order.service.dataaccess", "org.example.system.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.example")
public class OrderServiceApplication {
    public static void main(String[] args) {
      SpringApplication.run(OrderServiceApplication.class, args);
    }
}
