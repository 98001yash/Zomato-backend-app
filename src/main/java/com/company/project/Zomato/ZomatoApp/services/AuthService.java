package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.dto.SignupDto;
import com.company.project.Zomato.ZomatoApp.dto.UserDto;

public interface  AuthService {

    String[] login(String email , String password);

    UserDto signup(SignupDto signupDto);

    RestaurantDto onboardNewRestaurant(Long userId, String hotelId);
}
