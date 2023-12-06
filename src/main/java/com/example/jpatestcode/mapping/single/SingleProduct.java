package com.example.jpatestcode.mapping.single;

import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SingleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "single_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "singleProduct")
    private List<Price> prices;
    @OneToMany(mappedBy = "singleProduct")
    private List<Category> categories;

}
