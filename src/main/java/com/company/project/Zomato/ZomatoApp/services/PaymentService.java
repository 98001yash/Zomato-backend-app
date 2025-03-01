package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.Payment;
import com.company.project.Zomato.ZomatoApp.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(OrderItem orderItem);
    Payment createNewPayment(OrderItem orderItem);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
