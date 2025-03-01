package com.company.project.Zomato.ZomatoApp.entities;

import com.company.project.Zomato.ZomatoApp.enums.OrderItemRequestStatus;
import com.company.project.Zomato.ZomatoApp.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_order_item_customer",columnList="customer_id")
})
public class OrderItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOffLocation;


    @CreationTimestamp
    private LocalDateTime requestedTime;

    @ManyToOne(fetch=FetchType.LAZY)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderItemRequestStatus orderItemRequestStatus;

    private Double fare;
}
