package com.company.project.Zomato.ZomatoApp.strategies;


import com.company.project.Zomato.ZomatoApp.enums.PaymentMethod;
import com.company.project.Zomato.ZomatoApp.strategies.Impl.CODPaymentStrategy;
import com.company.project.Zomato.ZomatoApp.strategies.Impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CODPaymentStrategy codPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
       return  switch(paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> codPaymentStrategy;
        };
    }
}
