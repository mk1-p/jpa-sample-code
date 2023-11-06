package com.example.jpatestcode.members.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSearchCondition {

    private String name;
    private Integer age;
    private Integer ageGoe;
    private Integer ageLoe;

    @Builder
    public MemberSearchCondition(String name, Integer age, Integer ageGoe, Integer ageLoe) {
        this.name = name;
        this.age = age;
        this.ageGoe = ageGoe;
        this.ageLoe = ageLoe;
    }
}
