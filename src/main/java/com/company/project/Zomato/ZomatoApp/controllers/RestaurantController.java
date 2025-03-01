package com.company.project.Zomato.ZomatoApp.controllers;


import com.company.project.Zomato.ZomatoApp.dto.CustomerDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemStartDto;
import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.services.CustomerService;
import com.company.project.Zomato.ZomatoApp.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final CustomerService customerService;
    @PostMapping("/acceptOrderItem/{orderItemRequestId}")
    public ResponseEntity<OrderItemDto> acceptOrderItem(@PathVariable Long orderItemRequestId){
        return ResponseEntity.ok(restaurantService.acceptOrderItem(orderItemRequestId));
    }


    @PostMapping("/startOrderItem/{orderItemRequestId}")
    private ResponseEntity<OrderItemDto> startOrderItem(@PathVariable Long orderItemRequestId,
                                                        @RequestBody OrderItemStartDto orderItemStartDto){
        return ResponseEntity.ok(restaurantService.startOrderItem(orderItemRequestId,orderItemStartDto.getOtp()));
    }

    @PostMapping("/endOrderItem/{orderItemId}")
    public ResponseEntity<OrderItemDto> endOrderItem(@PathVariable Long orderItemId){
        return ResponseEntity.ok(restaurantService.endOrderItem(orderItemId));
    }

    @PostMapping("/cancelOrderItem/{orderItemId}")
    public ResponseEntity<OrderItemDto> cancelOrderItem(@PathVariable Long orderItemId){
        return ResponseEntity.ok(restaurantService.cancelOrderItem(orderItemId));
    }
    @GetMapping("/getMyProfile")
    public ResponseEntity<RestaurantDto> getMyProfile(){
        return ResponseEntity.ok(restaurantService.getMyProfile());
    }

    @GetMapping("/getMyOrderItems")
    public ResponseEntity<Page<OrderItemDto>> getAllMyOrderItem(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                                @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize);
        return ResponseEntity.ok((Page<OrderItemDto>) restaurantService.getAllMyOrderItem(pageRequest));

    }

    @PostMapping("/rateCustomer/{customerId}/{rating}")
    public ResponseEntity<CustomerDto> rateCustomer(@PathVariable Long customerId,@PathVariable Integer rating){
        return ResponseEntity.ok(restaurantService.rateCustomer(customerId,rating));
    }
    @PostMapping("/rateRestaurant/{restaurantId}/{rating}")
    public ResponseEntity<RestaurantDto> rateRestaurant(@PathVariable Long restaurantId,@PathVariable Integer rating){
        return ResponseEntity.ok(customerService.rateRestaurant(restaurantId,rating));
    }


}
