package com.example.jpatestcode.mapping.joined;

import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JoinedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joined_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "joinedProduct")
    private List<Price> prices;
    @OneToMany(mappedBy = "joinedProduct")
    private List<Category> categories;

    public JoinedProduct(Long id, String name, List<Price> prices, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.prices = prices;
        this.categories = categories;
    }
}
