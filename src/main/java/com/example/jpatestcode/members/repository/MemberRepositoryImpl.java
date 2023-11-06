package com.example.jpatestcode.members.repository;

import com.example.jpatestcode.members.Member;
import com.example.jpatestcode.members.dto.MemberSearchCondition;
import com.example.jpatestcode.members.dto.PreMemberDto;
import com.example.jpatestcode.members.dto.QPreMemberDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

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
                .where(usernameEq(condition.getUsernameCond()).or(ageEq(condition.getAgeCond())))
                .fetch();
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

}
