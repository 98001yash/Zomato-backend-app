package com.company.project.Zomato.ZomatoApp.services.Impl;

import com.company.project.Zomato.ZomatoApp.dto.CustomerDto;
import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.Rating;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.CustomerRepository;
import com.company.project.Zomato.ZomatoApp.repositories.RatingRepository;
import com.company.project.Zomato.ZomatoApp.repositories.RestaurantRepository;
import com.company.project.Zomato.ZomatoApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public RestaurantDto rateRestaurant(OrderItem orderItem, Integer rating) {
        Restaurant restaurant = orderItem.getRestaurant();
     Rating ratingObj = ratingRepository.findByOrderItem(orderItem)
             .orElseThrow(()-> new ResourceNotFoundException("Rating not found with id :"+orderItem.getId()));
     ratingObj.setRestaurantRating(rating);

     ratingRepository.save(ratingObj);
   Double newRating =  ratingRepository.findByRestaurant(restaurant)
             .stream()
             .mapToDouble(Rating::getCustomerRating)
             .average().orElse(0.0);
   restaurant.setRating(newRating);
  Restaurant savedRestaurant =  restaurantRepository.save(restaurant);
  return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @Override
    public CustomerDto rateCustomer(OrderItem orderItem, Integer rating) {
        Customer customer = orderItem.getCustomer();
        Rating ratingObj = ratingRepository.findByOrderItem(orderItem)
                .orElseThrow(()->new ResourceNotFoundException("Rating not found with id: "+orderItem.getId()));
        ratingObj.setCustomerRating(rating);

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByCustomer(customer)
                .stream()
                .mapToDouble(Rating::getRestaurantRating)
                .average().orElse(0.0);
        customer.setRating(newRating);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);

    }

    @Override
    public void createNewRating(OrderItem orderItem) {
        Rating rating = Rating.builder()
                .customer(orderItem.getCustomer())
                .restaurant(orderItem.getRestaurant())
                .orderItem(orderItem)
                .build();
        ratingRepository.save(rating);
    }
}
