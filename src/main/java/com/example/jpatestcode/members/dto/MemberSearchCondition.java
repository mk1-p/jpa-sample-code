package com.example.jpatestcode.members.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSearchCondition {

    private String usernameCond;
    private Integer ageCond;

    @Builder
    public MemberSearchCondition(String usernameCond, Integer ageCond) {
        this.usernameCond = usernameCond;
        this.ageCond = ageCond;
    }
}
