package com.company.project.Zomato.ZomatoApp.entities;


import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_restaurant_hotel_id",columnList = "hotelId")
})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Double rating;
    private Boolean available;
    private String hotelId;

    @Column(columnDefinition = "Geometry(Point, 4326)")
   private  Point currentLocation;


}
