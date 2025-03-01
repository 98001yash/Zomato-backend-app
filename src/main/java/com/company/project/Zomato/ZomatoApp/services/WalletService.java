package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.User;
import com.company.project.Zomato.ZomatoApp.entities.Wallet;
import com.company.project.Zomato.ZomatoApp.enums.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount, String transactionId, OrderItem orderItem, TransactionMethod transactionMethod);
    void withdrawAllMoneyFromWallet();

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, OrderItem orderItem, TransactionMethod transactionMethod);

    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
