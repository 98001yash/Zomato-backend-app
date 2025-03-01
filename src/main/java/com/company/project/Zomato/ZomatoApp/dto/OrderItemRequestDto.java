package com.company.project.Zomato.ZomatoApp.dto;

import com.company.project.Zomato.ZomatoApp.enums.OrderItemRequestStatus;
import com.company.project.Zomato.ZomatoApp.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestDto {

    private Long id;

    private Double fare;
    private PointDto pickupLocation;
    private PointDto dropOffLocation;
    private LocalDateTime requestedTime;
    private CustomerDto customer;
    private PaymentMethod paymentMethod;
    private OrderItemRequestStatus orderItemRequestStatus;
}
