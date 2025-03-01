package com.company.project.Zomato.ZomatoApp.strategies.Impl;

import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.Payment;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.enums.PaymentStatus;
import com.company.project.Zomato.ZomatoApp.enums.TransactionMethod;
import com.company.project.Zomato.ZomatoApp.repositories.PaymentRepository;
import com.company.project.Zomato.ZomatoApp.services.PaymentService;
import com.company.project.Zomato.ZomatoApp.services.WalletService;
import com.company.project.Zomato.ZomatoApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


    @Override
    public void processPayment(Payment payment) {
        Restaurant restaurant = payment.getOrderItem().getRestaurant();
        Customer customer = payment.getOrderItem().getCustomer();
        walletService.deductMoneyFromWallet(customer.getUser(),
              payment.getAmount(), null, payment.getOrderItem(), TransactionMethod.ORDERITEM);
        double restaurantCut = payment.getAmount() * (1 -PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(restaurant.getUser(),
                restaurantCut, null,payment.getOrderItem(),TransactionMethod.ORDERITEM);

      payment.setPaymentStatus(PaymentStatus.CONFIRMED);
      paymentRepository.save(payment);
    }
}

