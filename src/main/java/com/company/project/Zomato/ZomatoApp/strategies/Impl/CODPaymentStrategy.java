package com.company.project.Zomato.ZomatoApp.strategies.Impl;

import com.company.project.Zomato.ZomatoApp.entities.Payment;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.entities.Wallet;
import com.company.project.Zomato.ZomatoApp.enums.PaymentStatus;
import com.company.project.Zomato.ZomatoApp.enums.TransactionMethod;
import com.company.project.Zomato.ZomatoApp.repositories.PaymentRepository;
import com.company.project.Zomato.ZomatoApp.services.WalletService;
import com.company.project.Zomato.ZomatoApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


// Customer = 100
// Restaurant  = 70 Deduct 30RS from Restaurant Wallet
@Service
@RequiredArgsConstructor
public class CODPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


    @Override
    public void processPayment(Payment payment) {
     Restaurant restaurant = payment.getOrderItem().getRestaurant();

     Wallet restaurantWallet = walletService.findByUser(restaurant.getUser());
     double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

     walletService.deductMoneyFromWallet(restaurant.getUser(),platformCommission,null,
             payment.getOrderItem(), TransactionMethod.ORDERITEM);


     payment.setPaymentStatus(PaymentStatus.CONFIRMED);
     paymentRepository.save(payment);

    }
}
