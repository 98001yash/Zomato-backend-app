package com.company.project.Zomato.ZomatoApp.repositories;

import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//   ST_Distance(point1, point2)
//   ST_DWithin(point1, 10000)


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
            "FROM restaurant d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<Restaurant> findTenNearestRestaurants(Point pickupLocation);



    @Query(value = "SELECT d.* " +
            "FROM restaurant d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 15000) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Restaurant> findTenNearByTopRatedRestaurant(Point pickupLocation);

    Optional<Restaurant> findByUser(User user);
}
