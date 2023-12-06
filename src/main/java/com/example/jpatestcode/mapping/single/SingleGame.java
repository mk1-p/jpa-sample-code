package com.example.jpatestcode.mapping.single;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("game")
public class SingleGame extends SingleProduct {


    private boolean isWindows;
    private boolean isMac;
    private boolean isLinux;


}
