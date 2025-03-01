package com.company.project.Zomato.ZomatoApp.services.Impl;


import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.User;
import com.company.project.Zomato.ZomatoApp.entities.Wallet;
import com.company.project.Zomato.ZomatoApp.entities.WalletTransaction;
import com.company.project.Zomato.ZomatoApp.enums.TransactionMethod;
import com.company.project.Zomato.ZomatoApp.enums.TransactionType;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.WalletRepository;
import com.company.project.Zomato.ZomatoApp.services.WalletService;
import com.company.project.Zomato.ZomatoApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;


    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, OrderItem orderItem, TransactionMethod transactionMethod) {
      Wallet wallet = findByUser(user);
    wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .orderItem(orderItem)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

  //      walletTransactionService.createNewWalletTransaction(walletTransaction);
        wallet.getTransactions().add(walletTransaction);

    return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMoneyFromWallet() {

    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, OrderItem orderItem, TransactionMethod transactionMethod) {
       Wallet wallet = findByUser(user);
       wallet.setBalance(wallet.getBalance() - amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .orderItem(orderItem)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(()-> new ResourceNotFoundException("Wallet not found with id: "+walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
       return walletRepository.findByUser(user)
               .orElseThrow(()-> new ResourceNotFoundException("wallet not found with id: "+user.getId()));
    }
}
