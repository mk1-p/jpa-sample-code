package com.example.jpatestcode.mapping.joined;


import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("art")
@PrimaryKeyJoinColumn(name = "art_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JoinedArt extends JoinedProduct {

    private String painter;
    private boolean isDigital;

    @Builder
    public JoinedArt(Long id, String name, Integer packageId, List<Price> prices, List<Category> categories, String painter, boolean isDigital) {
        super(id, name, packageId, prices, categories);
        this.painter = painter;
        this.isDigital = isDigital;
    }
}
