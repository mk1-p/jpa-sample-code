package com.example.jpatestcode.mapping.joined;


import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("art")
@PrimaryKeyJoinColumn(name = "art_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class JoinedArt extends JoinedProduct {

    private String painter;
    private boolean isDigital;

    public JoinedArt(Long id, String name, List<Price> prices, List<Category> categories, String painter, boolean isDigital) {
        super(id, name, prices, categories);
        this.painter = painter;
        this.isDigital = isDigital;
    }
}
