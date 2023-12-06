package com.example.jpatestcode.mapping.superclass;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SuperMusic extends SuperProduct {


    private String artist;

}

