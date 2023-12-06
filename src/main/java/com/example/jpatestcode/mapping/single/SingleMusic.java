package com.example.jpatestcode.mapping.single;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("music")
public class SingleMusic extends SingleProduct {


    private String artist;

}

