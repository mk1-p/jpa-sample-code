package com.example.jpatestcode.mapping.joined;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("game")
@PrimaryKeyJoinColumn(name = "game_id")
public class JoinedGame extends JoinedProduct {


    private boolean isWindows;
    private boolean isMac;
    private boolean isLinux;


}
