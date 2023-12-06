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
@DiscriminatorValue("music")
@PrimaryKeyJoinColumn(name = "music_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class JoinedMusic extends JoinedProduct {
    private String artist;

    public JoinedMusic(Long id, String name, List<Price> prices, List<Category> categories, String artist) {
        super(id, name, prices, categories);
        this.artist = artist;
    }
}

