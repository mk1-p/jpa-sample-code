package com.example.jpatestcode.querydsl;


import com.example.jpatestcode.members.dto.MemberSearchCondition;
import com.example.jpatestcode.members.dto.PreMemberDto;
import com.example.jpatestcode.members.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @DisplayName("QueryDsl Simple Pagination 테스트")
    @Test
    void searchPageSimpleTest() {
        //given
        Integer ageGoe = 0;
        Integer ageLoe = 40;
        int pageSize = 10;
        MemberSearchCondition condition = MemberSearchCondition.builder()
                .ageGoe(ageGoe)
                .ageLoe(ageLoe)
                .build();
        PageRequest pageable = PageRequest.of(0, pageSize);

        //when
        Page<PreMemberDto> results = memberRepository.searchPageSimple(condition, pageable);

        //then
        List<PreMemberDto> contents = results.getContent();
        long count = results.getTotalElements();
        int totalPages = results.getTotalPages();
        System.out.println("contents count : "+contents.size());
        System.out.println("total count : "+count);
        System.out.println("total pages : "+totalPages);

        for (PreMemberDto content : contents) {
            System.out.println(content.toString());
        }

        // 요청 페이지 숫자보다 적거나 같은 경우 통과
        Assertions.assertThat(contents.size()).isLessThanOrEqualTo(pageSize);

    }


    @DisplayName("QueryDsl Complex Pagination 테스트")
    @Test
    void searchPageComplexTest() {
        //given
        Integer ageGoe = 0;
        Integer ageLoe = 40;
        int pageSize = 10;
        MemberSearchCondition condition = MemberSearchCondition.builder()
                .ageGoe(ageGoe)
                .ageLoe(ageLoe)
                .build();
        PageRequest pageable = PageRequest.of(0, pageSize);

        //when
        Page<PreMemberDto> results = memberRepository.searchPageComplex(condition, pageable);

        //then
        List<PreMemberDto> contents = results.getContent();
        long count = results.getTotalElements();
        int totalPages = results.getTotalPages();
        System.out.println("contents count : "+contents.size());
        System.out.println("total count : "+count);
        System.out.println("total pages : "+totalPages);

        for (PreMemberDto content : contents) {
            System.out.println(content.toString());
        }

        // 요청 페이지 숫자보다 적거나 같은 경우 통과
        Assertions.assertThat(contents.size()).isLessThanOrEqualTo(pageSize);

    }


    @DisplayName("QueryDsl Pagination 테스트")
    @Test
    void searchPageOptimizationTest() {
        //given
        Integer ageGoe = 0;
        Integer ageLoe = 40;
        int pageSize = 100;
        MemberSearchCondition condition = MemberSearchCondition.builder()
                .ageGoe(ageGoe)
                .ageLoe(ageLoe)
                .build();
        PageRequest pageable = PageRequest.of(0, pageSize);

        //when
        Page<PreMemberDto> results = memberRepository.searchPageOptimization(condition, pageable);


        //then
        List<PreMemberDto> contents = results.getContent();
        long count = results.getTotalElements();
        int totalPages = results.getTotalPages();
        System.out.println("contents count : "+contents.size());
        System.out.println("total count : "+count);
        System.out.println("total pages : "+totalPages);

        for (PreMemberDto content : contents) {
            System.out.println(content.toString());
        }

        // 요청 페이지 숫자보다 적거나 같은 경우 통과
        Assertions.assertThat(contents.size()).isLessThanOrEqualTo(pageSize);

    }

}
