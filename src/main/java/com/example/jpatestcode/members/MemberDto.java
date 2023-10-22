package com.example.jpatestcode.members;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter                 // Setter 방식을 이용할 경우 필요! ex Projections.bean()
@NoArgsConstructor      // DTO 반환 시, 기본 생성자로 먼저 생성하기 때문에 필요!
@ToString
public class MemberDto {

    private Long id;
    private String name;
    private Integer age;

    public MemberDto(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
