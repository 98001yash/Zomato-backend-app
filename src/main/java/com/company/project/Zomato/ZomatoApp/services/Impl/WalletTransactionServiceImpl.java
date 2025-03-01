package com.company.project.Zomato.ZomatoApp.services.Impl;


import com.company.project.Zomato.ZomatoApp.dto.WalletTransactionDto;
import com.company.project.Zomato.ZomatoApp.entities.WalletTransaction;
import com.company.project.Zomato.ZomatoApp.repositories.WalletTransactionRepository;
import com.company.project.Zomato.ZomatoApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;


    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
   walletTransactionRepository.save(walletTransaction);
    }
}
