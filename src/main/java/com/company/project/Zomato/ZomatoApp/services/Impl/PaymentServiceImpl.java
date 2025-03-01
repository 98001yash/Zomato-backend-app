package com.company.project.Zomato.ZomatoApp.services.Impl;

import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.Payment;
import com.company.project.Zomato.ZomatoApp.enums.PaymentStatus;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.PaymentRepository;
import com.company.project.Zomato.ZomatoApp.services.PaymentService;
import com.company.project.Zomato.ZomatoApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

private final PaymentStrategyManager paymentStrategyManager;
private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(OrderItem orderItem) {
        Payment payment  = paymentRepository.findByOrderItem(orderItem)
                .orElseThrow(()->new ResourceNotFoundException("Payment not found for orderItem "+orderItem.getId()));
    paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(OrderItem orderItem) {
        Payment payment = Payment.builder()
                .orderItem(orderItem)
                .paymentMethod(orderItem.getPaymentMethod())
                .amount(orderItem.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return  paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
       paymentRepository.save(payment);
    }
}
