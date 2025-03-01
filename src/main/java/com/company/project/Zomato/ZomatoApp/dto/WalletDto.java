package com.company.project.Zomato.ZomatoApp.dto;


import com.company.project.Zomato.ZomatoApp.entities.User;
import com.company.project.Zomato.ZomatoApp.entities.WalletTransaction;
import lombok.Data;

import java.util.List;

@Data
public class WalletDto {
    private Long id;
    private UserDto user;
    private Double balance;
    private List<WalletTransactionDto> transactions;
}
