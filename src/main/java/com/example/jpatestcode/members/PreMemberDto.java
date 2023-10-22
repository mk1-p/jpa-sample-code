package com.example.jpatestcode.members;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PreMemberDto {


    private Long id;
    private String name;
    private Integer age;

    @QueryProjection
    public PreMemberDto(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
