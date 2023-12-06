package com.example.jpatestcode.mapping.single;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("art")
public class SingleArt extends SingleProduct {

    private String painter;
    private boolean isDigital;

}
