package org.example.restaurant.service.domain.ports.output.repository;


import org.example.restaurant.service.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
