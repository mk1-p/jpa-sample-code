package com.example.jpatestcode.members.repository;

import com.example.jpatestcode.boards.QBoard;
import com.example.jpatestcode.members.Member;
import com.example.jpatestcode.members.dto.MemberSearchCondition;
import com.example.jpatestcode.members.dto.PreMemberDto;
import com.example.jpatestcode.members.dto.QPreMemberDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.example.jpatestcode.boards.QBoard.*;
import static com.example.jpatestcode.members.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<PreMemberDto> search(MemberSearchCondition condition) {

        return queryFactory
                .select(new QPreMemberDto(member.id, member.name, member.age))
                .from(member)
                .where(
                        usernameLike(condition.getName())
//                                .and(ageGoe(condition.getAgeGoe()))
//                                .and(ageLoe(condition.getAgeLoe()))
                                .and(ageBetween(condition.getAgeGoe(), condition.getAgeLoe()))
                )
                .fetch();
    }

    @Override
    public Page<PreMemberDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<PreMemberDto> results = queryFactory
                .select(new QPreMemberDto(member.id, member.name, member.age))
                .from(member)
                .leftJoin(member.boards, board)
                .where(
                        usernameLike(condition.getName())
                                .and(ageBetween(condition.getAgeGoe(), condition.getAgeLoe()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(member.id)
                .fetchResults();

        List<PreMemberDto> contents = results.getResults();
        long count = results.getTotal();

        return new PageImpl<>(contents,pageable,count);

    }

    @Override
    public Page<PreMemberDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {

        List<PreMemberDto> contents = queryFactory
                .select(new QPreMemberDto(member.id, member.name, member.age))
                .from(member)
                .leftJoin(member.boards, board)
                .where(
                        usernameLike(condition.getName())
                                .and(ageBetween(condition.getAgeGoe(), condition.getAgeLoe()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(member.id)
                .fetch();

        long count = queryFactory
                .selectFrom(member)
                .where(
                        usernameLike(condition.getName())
                                .and(ageBetween(condition.getAgeGoe(), condition.getAgeLoe()))
                )
                .fetchCount();

        return new PageImpl<>(contents, pageable, count);
    }

    @Override
    public Page<PreMemberDto> searchPageOptimization(MemberSearchCondition condition, Pageable pageable) {
        List<PreMemberDto> contents = queryFactory
                .select(new QPreMemberDto(member.id, member.name, member.age))
                .from(member)
                .where(
                        usernameLike(condition.getName())
                                .and(ageBetween(condition.getAgeGoe(), condition.getAgeLoe()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCountQuery = queryFactory
                .select(member.count())
                .from(member)
                .where(
                        usernameLike(condition.getName())
                                .and(ageBetween(condition.getAgeGoe(), condition.getAgeLoe()))
                );

        // 카운트 쿼리가 작동하지 않는 경우 (굳이 작동하지 않아도 되는 경우)
        // 첫페이지에서 리밋을 숫자 보다 컨텐츠 갯수가 적은 경우
        // 마지막 페이지일 떄
        return PageableExecutionUtils.getPage(contents, pageable, totalCountQuery::fetchOne);
    }


    private BooleanExpression usernameLike(String usernameCond) {
        // username 컨디션 값이
        // null이 아닌 경우 like
        // null인 경우 name 이 필수 값이라는 가정하에 is not null로 반환값이 null이 아니도록 세팅
        // BooleanExpression이 null 인 경우 .and() .or() 연결이 불가능 하므로 설계상 고려점
        return usernameCond != null ? member.name.like(usernameCond) : member.name.isNotNull();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        if (usernameCond == null) {
            return null;    // null 을 반환 시에 where에서 해당 조건을 무시하고 넘어간다.
        }
        return member.name.eq(usernameCond);
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }
    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }
    // 기존 메소드 연결로 만들어낼 수 있다.
    private BooleanExpression ageBetween(int ageGoeCond, int ageLoeCond) {
        return ageGoe(ageGoeCond).and(ageLoe(ageLoeCond));
    }

}
