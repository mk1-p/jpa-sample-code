package com.example.jpatestcode.querydsl;


import com.example.jpatestcode.members.dto.MemberSearchCondition;
import com.example.jpatestcode.members.dto.PreMemberDto;
import com.example.jpatestcode.members.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

public class QueryDslFuncTest {


    private final MemberRepository memberRepository;

    @Autowired
    public QueryDslFuncTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @DisplayName("Custom Repository Search 메소드 테스트")
    @Test
    void searchTest() {
        // given
        String name = "Kim";
        Integer ageGoe = 15;
        Integer ageLoe = 25;
        MemberSearchCondition condition = MemberSearchCondition.builder()
                .name(name)
                .ageGoe(ageGoe)
                .ageLoe(ageLoe)
                .build();

        // when
        List<PreMemberDto> results = memberRepository.search(condition);

        // then
        for (PreMemberDto result : results) {
            System.out.println(result.toString());
        }


        Assertions.assertThat(results.size()).isEqualTo(1);
        Assertions.assertThat(results.get(0).getName()).isEqualTo("Kim");

    }

}
