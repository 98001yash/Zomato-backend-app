package com.company.project.Zomato.ZomatoApp.services.Impl;

import com.company.project.Zomato.ZomatoApp.dto.OrderItemRequestDto;
import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.enums.OrderItemRequestStatus;
import com.company.project.Zomato.ZomatoApp.enums.OrderItemStatus;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.OrderItemRepository;
import com.company.project.Zomato.ZomatoApp.services.OrderItemRequestService;
import com.company.project.Zomato.ZomatoApp.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemRequestService orderItemRequestService;
    private final ModelMapper modelMapper;


    @Override
    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(()->  new ResourceNotFoundException("OrderItem not found with id: "+orderItemId));
    }

    @Override
    public OrderItem createNewOrderItem(OrderItemRequest orderItemRequest, Restaurant restaurant) {
        orderItemRequest.setOrderItemRequestStatus(OrderItemRequestStatus.CONFIRMED);
     OrderItem orderItem = modelMapper.map(orderItemRequest,OrderItem.class);
     orderItem.setOrderItemStatus(OrderItemStatus.CONFIRMED);
     orderItem.setRestaurant(restaurant);
     orderItem.setOtp(generateRandomOtp());
     orderItem.setId(null);

     orderItemRequestService.update(orderItemRequest);
     return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItemStatus(OrderItem orderItem, OrderItemStatus orderItemStatus) {
        orderItem.setOrderItemStatus(orderItemStatus);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Page<OrderItem> getAlOrderItemsOfCustomer(Customer customer , PageRequest pageRequest) {
        return orderItemRepository.findByCustomer(customer, pageRequest);
    }

    @Override
    public Page<OrderItem> getAllOrderItemOfRestaurant(Restaurant restaurant, PageRequest pageRequest) {
        return orderItemRepository.findByRestaurant(restaurant, pageRequest);
    }

    private String generateRandomOtp(){
        Random random  = new Random();
       int otp =  random.nextInt(10000);
        return String.format("%04d",otp);
    }
}

