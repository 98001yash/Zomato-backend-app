package com.company.project.Zomato.ZomatoApp.repositories;

import com.company.project.Zomato.ZomatoApp.entities.User;
import com.company.project.Zomato.ZomatoApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
