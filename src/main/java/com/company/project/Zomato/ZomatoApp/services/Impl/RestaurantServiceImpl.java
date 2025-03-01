package com.company.project.Zomato.ZomatoApp.services.Impl;

import com.company.project.Zomato.ZomatoApp.dto.CustomerDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemDto;
import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.entities.User;
import com.company.project.Zomato.ZomatoApp.enums.OrderItemRequestStatus;
import com.company.project.Zomato.ZomatoApp.enums.OrderItemStatus;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.RestaurantRepository;
import com.company.project.Zomato.ZomatoApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final OrderItemRequestService orderItemRequestService;
    private final OrderItemService orderItemService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;


    @Override
    @Transactional
    public OrderItemDto acceptOrderItem(Long orderItemRequestId) {
    OrderItemRequest orderItemRequest = orderItemRequestService.findOrderItemRequestById(orderItemRequestId);
    if(!orderItemRequest.getOrderItemRequestStatus().equals(OrderItemRequestStatus.PENDING)){
        throw new RuntimeException("Order item request can not be accepted, status is: "+orderItemRequest.getOrderItemRequestStatus());
    }
    Restaurant currentRestaurant = getCurrentRestaurant();
    if(!currentRestaurant.getAvailable()) {
        throw new RuntimeException("Restaurant is  not available to take your orders");
    }

    currentRestaurant.setAvailable(true);
    Restaurant savedRestaurant = restaurantRepository.save(currentRestaurant);

   OrderItem orderItem =  orderItemService.createNewOrderItem(orderItemRequest, savedRestaurant);
  return modelMapper.map(orderItem, OrderItemDto.class);

    }


    @Override
    public OrderItemDto startOrderItem(Long orderItemId, String otp) {
       OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
           Restaurant restaurant = getCurrentRestaurant();

           if(!restaurant.equals(orderItem.getRestaurant())){
               throw new RuntimeException("Restaurant can not start the orderItem as he has not accepted it earlier");
           }
           if(!orderItem.getOrderItemStatus().equals(OrderItemStatus.CONFIRMED)){
               throw new RuntimeException("OrderItem Status is not confirmed hence cannot be started,  status"+ orderItem.getOrderItemStatus());
           }

           if(!otp.equals(orderItem.getOtp())){
               throw new RuntimeException("otp is not valid, otp: "+otp);
           }
           orderItem.setStartedAt(LocalDateTime.now());

         OrderItem savedOrderItem = orderItemService.updateOrderItemStatus(orderItem, OrderItemStatus.ONGOING);
         paymentService.createNewPayment(savedOrderItem);
         ratingService.createNewRating(savedOrderItem);
          return modelMapper.map(savedOrderItem, OrderItemDto.class);
        }

    @Override
    public OrderItemDto cancelOrderItem(Long orderItemId) {

       OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
       Restaurant restaurant = getCurrentRestaurant();
       if(!restaurant.equals(orderItem.getRestaurant())){
           throw new RuntimeException("Restaurant cannot start a ride as he has not accepted it earlier.");
       }
       if(!orderItem.getOrderItemStatus().equals(OrderItemStatus.CONFIRMED)){
           throw new RuntimeException("OrderItem cannot be cancelled, invalid status: "+orderItem.getOrderItemStatus());
       }
       orderItemService.updateOrderItemStatus(orderItem, OrderItemStatus.CANCELLED);
       restaurant.setAvailable(true);
       restaurantRepository.save(restaurant);
       return modelMapper.map(orderItem, OrderItemDto.class);
    }


    @Override
    @Transactional
    public OrderItemDto endOrderItem(Long orderItemId) {
      OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
      Restaurant restaurant = getCurrentRestaurant();

      if(!restaurant.equals(orderItem.getRestaurant())){
          throw new RuntimeException("Restaurant cannot start a orderItem as he has not accepted it earlier..");
      }
      if(!orderItem.getOrderItemStatus().equals(OrderItemStatus.ONGOING)){
          throw new RuntimeException("OrderItem status is not ONGOING hence cannot be ended , status: "+orderItem.getOrderItemStatus());
      }
      orderItem.setEndedAt(LocalDateTime.now());
      OrderItem savedOrderItem = orderItemService.updateOrderItemStatus(orderItem, OrderItemStatus.ENDED);
      updateRestaurantAvailability(restaurant, true);

      paymentService.processPayment(orderItem);
      return modelMapper.map(savedOrderItem, OrderItemDto.class);
    }



    @Override
    public CustomerDto rateCustomer(Long  orderItemId, Integer rating) {
      OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
      Restaurant restaurant = getCurrentRestaurant();
      if(!restaurant.equals(orderItem.getRestaurant())){
          throw new RuntimeException("Restaurant is not thw owner of the orderItem.");
      }

      if(!orderItem.getOrderItemStatus().equals(OrderItemStatus.ENDED)){
          throw new RuntimeException("OrderItem status is not ENDED hence cannot start rating: status: "+orderItem.getOrderItemStatus());
      }

     return  ratingService.rateCustomer(orderItem, rating);

    }

    @Override
    public RestaurantDto getMyProfile() {
      Restaurant currentRestaurant = getCurrentRestaurant();
      return modelMapper.map(currentRestaurant,RestaurantDto.class);
    }

    @Override
    public Page<OrderItemDto> getAllMyOrderItem(PageRequest pageRequest) {
    Restaurant currentRestaurant = getCurrentRestaurant();
    return orderItemService.getAllOrderItemOfRestaurant(currentRestaurant, pageRequest).map(
            orderItem -> modelMapper.map(orderItem, OrderItemDto.class)
    );
    }

    @Override
    public Restaurant getCurrentRestaurant() {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return restaurantRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Restaurant not associated with user with id: "+user.getId()));
    }

    @Override
    public Restaurant updateRestaurantAvailability(Restaurant restaurant, boolean available) {
        restaurant.setAvailable(available);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant createNewRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
