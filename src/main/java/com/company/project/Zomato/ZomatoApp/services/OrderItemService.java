package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.dto.OrderItemRequestDto;
import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.enums.OrderItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderItemService {

    OrderItem getOrderItemById(Long orderItemId);


    OrderItem createNewOrderItem(OrderItemRequest orderItemRequest, Restaurant restaurant);

    OrderItem updateOrderItemStatus(OrderItem orderItem, OrderItemStatus orderItemStatus);

    Page<OrderItem> getAlOrderItemsOfCustomer(Customer customer, PageRequest pageRequest);

    Page<OrderItem> getAllOrderItemOfRestaurant(Restaurant restaurant, PageRequest pageRequest);

}
