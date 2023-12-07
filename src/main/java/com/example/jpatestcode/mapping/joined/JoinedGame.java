package com.example.jpatestcode.mapping.joined;


import com.example.jpatestcode.mapping.Category;
import com.example.jpatestcode.mapping.Price;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@DiscriminatorValue("game")
@PrimaryKeyJoinColumn(name = "game_id")
@NoArgsConstructor
@Getter
@ToString
public class JoinedGame extends JoinedProduct {


    private boolean isWindows;
    private boolean isMac;
    private boolean isLinux;


    @Builder
    public JoinedGame(Long id, String name, Integer packageId, List<Price> prices, List<Category> categories, boolean isWindows, boolean isMac, boolean isLinux) {
        super(id, name, packageId, prices, categories);
        this.isWindows = isWindows;
        this.isMac = isMac;
        this.isLinux = isLinux;
    }
}
