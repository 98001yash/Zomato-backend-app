package com.company.project.Zomato.ZomatoApp.repositories;


import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.Rating;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCustomer(Customer customer);
    List<Rating> findByRestaurant(Restaurant restaurant);

    Optional<Rating> findByOrderItem(OrderItem orderItem);
}
