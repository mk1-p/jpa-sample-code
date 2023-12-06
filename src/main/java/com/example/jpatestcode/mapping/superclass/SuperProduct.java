package com.example.jpatestcode.mapping.superclass;

import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;

import java.util.List;


@MappedSuperclass
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SuperProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "super_id")
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "super_id")
    private List<Price> prices;
    @OneToMany
    @JoinColumn(name = "super_id")
    private List<Category> categories;

}
