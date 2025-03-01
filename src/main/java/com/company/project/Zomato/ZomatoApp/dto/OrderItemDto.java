package com.company.project.Zomato.ZomatoApp.dto;


import com.company.project.Zomato.ZomatoApp.enums.OrderItemStatus;
import com.company.project.Zomato.ZomatoApp.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long id;
    private PointDto pickupLocation;
    private PointDto dropOffLocation;
    private LocalDateTime createdTime;
    private CustomerDto customer;
    private RestaurantDto restaurant;
    private String otp;
    private PaymentMethod paymentMethod;
    private OrderItemStatus orderItemStatus;
    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}

