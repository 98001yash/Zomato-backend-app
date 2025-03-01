package com.company.project.Zomato.ZomatoApp.repositories;

import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByOrderItem(OrderItem orderItem);
}
