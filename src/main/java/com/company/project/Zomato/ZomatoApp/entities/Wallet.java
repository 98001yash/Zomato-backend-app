package com.company.project.Zomato.ZomatoApp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade= CascadeType.DETACH)
    private User user;

    private Double balance = 0.0;

    @OneToMany(mappedBy="wallet", fetch = FetchType.LAZY)
    private List<WalletTransaction> transactions;
}
