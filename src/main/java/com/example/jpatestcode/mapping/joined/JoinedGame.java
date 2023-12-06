package com.example.jpatestcode.mapping.joined;


import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("game")
@PrimaryKeyJoinColumn(name = "game_id")
@NoArgsConstructor
@SuperBuilder
public class JoinedGame extends JoinedProduct {


    private boolean isWindows;
    private boolean isMac;
    private boolean isLinux;


    public JoinedGame(Long id, String name, List<Price> prices, List<Category> categories, boolean isWindows, boolean isMac, boolean isLinux) {
        super(id, name, prices, categories);
        this.isWindows = isWindows;
        this.isMac = isMac;
        this.isLinux = isLinux;
    }
}
