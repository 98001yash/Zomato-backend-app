package com.company.project.Zomato.ZomatoApp.dto;


import com.company.project.Zomato.ZomatoApp.enums.TransactionMethod;
import com.company.project.Zomato.ZomatoApp.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDto {

    private Long id;
    private Double amount;

    private TransactionType transactionType;
    private TransactionMethod transactionMethod;

    private OrderItemDto orderItem;
    private String transactionId;
    private WalletDto wallet;
    private LocalDateTime timeStamp;
}
