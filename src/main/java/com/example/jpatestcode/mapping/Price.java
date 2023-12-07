package com.example.jpatestcode.mapping;


import com.example.jpatestcode.mapping.joined.JoinedProduct;
import com.example.jpatestcode.mapping.single.SingleProduct;
import com.example.jpatestcode.mapping.superclass.SuperProduct;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long id;
    private Integer cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joined_id")
    private JoinedProduct joinedProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "single_id")
    private SingleProduct singleProduct;

    @Column(name = "super_id")
    private Long superId;

    @CreatedBy
    private LocalDateTime createdDt;

    @Builder
    public Price(Long id, Integer cost, JoinedProduct joinedProduct, SingleProduct singleProduct, Long superId) {
        this.id = id;
        this.cost = cost;
        this.joinedProduct = joinedProduct;
        this.singleProduct = singleProduct;
        this.superId = superId;
    }
}
