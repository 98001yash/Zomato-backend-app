package com.company.project.Zomato.ZomatoApp.strategies;

import com.company.project.Zomato.ZomatoApp.entities.Payment;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.3;


    void processPayment(Payment Payment);

}
