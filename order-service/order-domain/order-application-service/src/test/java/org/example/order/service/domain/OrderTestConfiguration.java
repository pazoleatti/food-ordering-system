package org.example.order.service.domain;

import org.example.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import org.example.order.service.domain.ports.output.message.publisher.restaurantapproval.RestaurantApprovalMessagePublisher;
import org.example.order.service.domain.ports.output.repository.*;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "org.example")
public class OrderTestConfiguration {

    @Bean
    public PaymentRequestMessagePublisher paymentRequestMessagePublisher() {
        return Mockito.mock(PaymentRequestMessagePublisher.class);
    }
    @Bean
    public RestaurantApprovalMessagePublisher restaurantApprovalMessagePublisher() {
        return Mockito.mock(RestaurantApprovalMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public PaymentOutboxRepository paymentOutboxRepository() {return Mockito.mock(PaymentOutboxRepository.class);}
    @Bean
    public ApprovalOutboxRepository approvalOutboxRepository() {return Mockito.mock(ApprovalOutboxRepository.class);}

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
