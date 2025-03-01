package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;

public interface OrderItemRequestService {

    OrderItemRequest findOrderItemRequestById(Long orderItemRequestId);

    void update(OrderItemRequest orderItemRequest);
}
