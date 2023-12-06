package com.example.jpatestcode.mapping.joined;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("art")
@PrimaryKeyJoinColumn(name = "art_id")
public class JoinedArt extends JoinedProduct {

    private String painter;
    private boolean isDigital;

}
