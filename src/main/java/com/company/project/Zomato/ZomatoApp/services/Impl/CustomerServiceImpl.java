package com.company.project.Zomato.ZomatoApp.services.Impl;

import com.company.project.Zomato.ZomatoApp.dto.CustomerDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemRequestDto;
import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.entities.*;
import com.company.project.Zomato.ZomatoApp.enums.OrderItemRequestStatus;
import com.company.project.Zomato.ZomatoApp.enums.OrderItemStatus;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.CustomerRepository;
import com.company.project.Zomato.ZomatoApp.repositories.OrderItemRequestRepository;
import com.company.project.Zomato.ZomatoApp.services.CustomerService;
import com.company.project.Zomato.ZomatoApp.services.OrderItemService;
import com.company.project.Zomato.ZomatoApp.services.RatingService;
import com.company.project.Zomato.ZomatoApp.services.RestaurantService;
import com.company.project.Zomato.ZomatoApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    private final ModelMapper modelMapper;
  private final RideStrategyManager rideStrategyManager;
    private final OrderItemRequestRepository orderItemRequestRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemService orderItemService;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public OrderItemRequestDto requestOrderItem(OrderItemRequestDto orderItemRequestDto) {
        Customer customer = getCurrentCustomer();
        OrderItemRequest orderItemRequest = modelMapper.map(orderItemRequestDto, OrderItemRequest.class);
       orderItemRequest.setOrderItemRequestStatus(OrderItemRequestStatus.PENDING);

       Double fare = rideStrategyManager.orderItemFareCalculationStrategy().calculateFare(orderItemRequest);
       orderItemRequest.setFare(fare);

       OrderItemRequest savedOrderItemRequest = orderItemRequestRepository.save(orderItemRequest);
      List<Restaurant> restaurants =  rideStrategyManager.restaurantMatchingStrategy(customer.getRating()).findTenNearestRestaurants(orderItemRequest);

      //    TODO send notification to all the restaurant about this orderItem Request

       return modelMapper.map(savedOrderItemRequest, OrderItemRequestDto.class);
    }

    @Override
    public OrderItemDto cancelOrderItem(Long orderItemId) {
        Customer customer= getCurrentCustomer();
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);

        if(!customer.equals(orderItem.getCustomer())){
            throw new RuntimeException("Customer does not own this orderItem with id: "+orderItem.getOrderItemStatus());
        }
        OrderItem savedOrderItem = orderItemService.updateOrderItemStatus(orderItem, OrderItemStatus.CANCELLED);
        restaurantService.updateRestaurantAvailability(orderItem.getRestaurant(),true);
     return modelMapper.map(savedOrderItem, OrderItemDto.class);
    }

    @Override
    public RestaurantDto rateRestaurant(Long orderItemId, Integer rating) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        Customer customer = getCurrentCustomer();
        if(!customer.equals(orderItem.getCustomer())){
            throw new RuntimeException("customer is not thw owner of the orderItem.");
        }

        if(!orderItem.getOrderItemStatus().equals(OrderItemStatus.ENDED)){
            throw new RuntimeException("OrderItem status is not ENDED hence cannot start rating: status: "+orderItem.getOrderItemStatus());
        }

        return  ratingService.rateRestaurant(orderItem, rating);

    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return List.of();
    }

    @Override
    public CustomerDto getMyProfile() {
        return null;
    }

    @Override
    public List<OrderItemDto> getAllMyOrderItem(PageRequest pageRequest) {
        return List.of();
    }

    @Override
    public Customer createNewCustomer(User user) {
        Customer customer = Customer.builder()
                .user(user)
                .rating(0.0)
                .build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCurrentCustomer() {

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customerRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException(
                "Customer not found with id: "+user.getId()
        ));
    }


}
