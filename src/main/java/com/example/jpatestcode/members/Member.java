package com.example.jpatestcode.members;


import com.example.jpatestcode.boards.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column
    private String name;
    @Column
    private Integer age;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards;

    @Column
    private String dummy;   // 임시 컬럼

    @Builder
    public Member(Long id, String name, Integer age, List<Board> boards) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.boards = boards;
    }
}
