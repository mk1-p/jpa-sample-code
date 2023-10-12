package com.example.jpatestcode.info;

import com.example.jpatestcode.members.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JoinColumns 관계를 가질 때,
 * Key 데이터를 가져올 경우 Lazy Loading을 하는가에 대한 Test Entity
 * Member : Info = 1 : N
 */
@Entity
@Table(name = "INFO")
@NoArgsConstructor
@Getter
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {
            @JoinColumn(name = "member_id", referencedColumnName = "member_id"),
            @JoinColumn(name = "name", referencedColumnName = "name")

    },foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Member member;      // Member와 연관관계를 나타내는 컬럼 2가지

    @Column(name = "text")
    private String text;


    @Builder
    public Info(Long id, Member member, String text) {
        this.id = id;
        this.member = member;
        this.text = text;
    }

}
