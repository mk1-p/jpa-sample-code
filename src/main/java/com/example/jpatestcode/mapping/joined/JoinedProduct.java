package com.example.jpatestcode.mapping.joined;

import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
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

}
