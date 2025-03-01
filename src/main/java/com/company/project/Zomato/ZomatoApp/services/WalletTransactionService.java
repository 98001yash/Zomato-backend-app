package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.dto.WalletTransactionDto;
import com.company.project.Zomato.ZomatoApp.entities.WalletTransaction;

public interface WalletTransactionService {


    void  createNewWalletTransaction(WalletTransaction walletTransaction);

}
