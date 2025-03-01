package com.company.project.Zomato.ZomatoApp.repositories;

import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findByCustomer(Customer customer, Pageable pageRequest);

    Page<OrderItem> findByRestaurant(Restaurant restaurant, Pageable pageRequest);
}
