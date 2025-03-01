package com.company.project.Zomato.ZomatoApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;
    private UserDto user;
    private Double  rating;
    private boolean available;
    private String HotelId;
    private String otp;
}
