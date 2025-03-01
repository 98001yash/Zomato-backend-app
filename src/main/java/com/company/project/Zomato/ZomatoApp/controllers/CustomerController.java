package com.company.project.Zomato.ZomatoApp.controllers;


import com.company.project.Zomato.ZomatoApp.dto.*;
import com.company.project.Zomato.ZomatoApp.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/requestOrderItem")
    public ResponseEntity<OrderItemRequestDto> requestOrderItem(@RequestBody OrderItemRequestDto orderItemRequestDto){
        return ResponseEntity.ok(customerService.requestOrderItem(orderItemRequestDto));
    }

    @PostMapping("/cancelOrderItem/{orderItemId}")
    public ResponseEntity<OrderItemDto> cancelOrderItem(@PathVariable Long orderItemId){
        return ResponseEntity.ok(customerService.cancelOrderItem(orderItemId));
    }

    @PostMapping("/rateRestaurant")
    public ResponseEntity<RestaurantDto> rateRestaurant(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(customerService.rateRestaurant(ratingDto.getOrderItemId(),ratingDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<CustomerDto> getMyProfile(){
        return ResponseEntity.ok(customerService.getMyProfile());
    }

    @GetMapping("/getMyOrderItems")
    public ResponseEntity<Page<OrderItemDto>> getAllMyOrderItem(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                                @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize);
        return ResponseEntity.ok((Page<OrderItemDto>) customerService.getAllMyOrderItem(pageRequest));

    }

}
