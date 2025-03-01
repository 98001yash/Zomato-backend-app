package com.company.project.Zomato.ZomatoApp.repositories;

import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRequestRepository extends JpaRepository<OrderItemRequest, Long> {
}
