package com.example.jpatestcode.mapping.joined;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("music")
@PrimaryKeyJoinColumn(name = "music_id")
public class JoinedMusic extends JoinedProduct {


    private String artist;

}

