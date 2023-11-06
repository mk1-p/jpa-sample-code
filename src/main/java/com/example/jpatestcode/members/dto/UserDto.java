package com.example.jpatestcode.members.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String username;
    private Integer age;

    public UserDto(Long id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }
}
