package org.example.customer.service;

import org.example.customer.service.domain.CustomerDomainService;
import org.example.customer.service.domain.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }
}
