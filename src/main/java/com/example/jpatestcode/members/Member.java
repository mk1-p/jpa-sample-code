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
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards;

    @Builder
    public Member(Long id, String name, List<Board> boards) {
        this.id = id;
        this.name = name;
        this.boards = boards;
    }
}
