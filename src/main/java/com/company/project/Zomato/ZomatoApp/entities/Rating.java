package com.company.project.Zomato.ZomatoApp.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name ="idx_rating_customer",columnList = "customer_id"),
        @Index(name="idx_rating_restaurant",columnList = "restaurant_id")
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private OrderItem orderItem;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;

    private Integer restaurantRating;
    private Integer customerRating;
}
