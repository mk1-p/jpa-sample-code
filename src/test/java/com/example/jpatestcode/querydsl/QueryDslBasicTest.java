package com.example.jpatestcode.querydsl;

import com.example.jpatestcode.boards.Board;
import com.example.jpatestcode.boards.QBoard;
import com.example.jpatestcode.boards.QBoardRepository;
import com.example.jpatestcode.members.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.jpatestcode.boards.QBoard.board;
import static com.example.jpatestcode.members.QMember.member;

@SpringBootTest
@Transactional
public class QueryDslBasicTest {

    private final QBoardRepository qBoardRepository;
    private final EntityManager em;
    private JPAQueryFactory query;

    @Autowired
    public QueryDslBasicTest(QBoardRepository qBoardRepository, EntityManager em) {
        this.qBoardRepository = qBoardRepository;
        this.em = em;
        this.query = new JPAQueryFactory(this.em);
    }

    @DisplayName("QueryDSL 모든 게시글 가져오기 테스트")
    @Test
    void getAllBoard() {
        // given

        // when
        List<Board> boards = qBoardRepository.findAllBoard();

        // then
        System.out.println("boards size : "+boards.size());
        for (Board board : boards) {
            System.out.println(board.toString());
        }

        Assertions.assertThat(boards.size()).isGreaterThan(4);

    }

    @DisplayName("QueryDSL 특정 아이디 값으로 조회 테스트")
    @Test
    void getBoardById() {
        // given
        Long id = 5L;

        // when
        Board result = qBoardRepository.findBoardById(id);

        // then
        Assertions.assertThat(result).isNotNull();              // 결과값이 null 인지 체크
        Assertions.assertThat(result.getId()).isEqualTo(id);    // 결과값의 id와 처음 세팅한 id 값이 같은지 체크

        System.out.println("result : "+result.toString());

    }

    @DisplayName("여러 결과 호출 방법")
    @Test
    void getResults() {
        // fetch -> List
        List<Board> fetch = query.selectFrom(board).fetch();
        // fetchOne -> 단건
        Board fetchOne = query.selectFrom(board).fetchOne();
        // fetchFirst -> limit(1).fetchOne()
        Board fetchFirst = query.selectFrom(board).fetchFirst();
        List<Board> results = query.selectFrom(board).fetchResults().getResults();

    }


    @DisplayName("Case 문을 테스트")
    @Test
    void caseTest() {
        // given
        String child = "미성년";
        String adult = "성인";

        // when
        List<String> results = query.select(new CaseBuilder()
                        .when(member.age.between(0, 19)).then(child)
                        .when(member.age.goe(20)).then(adult)
                        .otherwise("기타"))
                .from(member)
                .fetch();


        // then
        for (String result : results) {
            System.out.println("result : "+result);
        }


    }


    @DisplayName("Case 문에 Rank 적용 테스트")
    @Test
    void caseRankTest() {
        // given
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(member.age.between(0, 19)).then(3)
                .when(member.age.between(20, 29)).then(2)
                .when(member.age.between(30, 39)).then(1)
                .otherwise(4);

        // when
        List<Tuple> fetch = query.select(member.name, member.age, rankPath)
                .from(member)
                .orderBy(rankPath.asc())
                .fetch();


        // then
        for (Tuple tuple : fetch) {
            String name = tuple.get(member.name);
            Integer age = tuple.get(member.age);
            Integer rank = tuple.get(rankPath);
            String message = String.format("name : %s age : %d ranke : %d", name, age, rank);
            System.out.println(message);
        }

        // 정렬 기준 첫 데이터가 1보다 작거나 같은가?
        Assertions.assertThat(fetch.get(0).get(rankPath)).isLessThanOrEqualTo(1);


    }


    @DisplayName("Concat으로 name과 age 혼합 테스트")
    @Test
    void concatElement() {

        // given
        Long id = 1l;

        // when
        String result = query.select(member.name.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();

        // then
        System.out.println(result);

        Assertions.assertThat(result).contains("10");


    }

    @DisplayName("Setter 매핑 테스트")
    @Test
    void dtoPropertyTest() {

        // given

        // when
        List<MemberDto> results = query.select(Projections.bean(MemberDto.class,
                        member.name,
                        member.age))
                .from(member)
                .fetch();

        // then
        for (MemberDto result : results) {
            System.out.println(result.toString());
        }

    }

    @DisplayName("필드 매핑 테스트")
    @Test
    void dtoFieldsTest() {

        // given

        // when
        List<MemberDto> results = query.select(Projections.fields(MemberDto.class,
                        member.name,
                        member.age))
                .from(member)
                .fetch();

        // then
        for (MemberDto result : results) {
            System.out.println(result.toString());
        }

    }


    @DisplayName("Alias 매핑 테스트")
    @Test
    void dtoAliasTest() {

        // given
        QMember memberSub = member;

        // when
        List<UserDto> results = query.select(Projections.fields(UserDto.class,
                        member.name.as("username"),     // 매핑하고자 하는 필드네임
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(memberSub.age.max())
                                        .from(memberSub), "age")
                        )
                )
                .from(member)
                .fetch();

        // then
        for (UserDto result : results) {
            System.out.println(result.toString());
        }

    }

    @DisplayName("Construct 매핑 테스트")
    @Test
    void dtoConstructTest() {

        // given

        // when
        List<MemberDto> results = query.select(Projections.constructor(MemberDto.class,
                        member.id,
                        member.name,
                        member.age))
                .from(member)
                .fetch();

        // then
        for (MemberDto result : results) {
            System.out.println(result.toString());
        }

    }

    @DisplayName("QueryProjection Anno 매핑 테스트")
    @Test
    void dtoQueryProjTest() {

        // given

        // when
        List<PreMemberDto> results = query.select(new QPreMemberDto(member.id,member.name,member.age))
                .from(member)
                .fetch();

        // then
        for (PreMemberDto result : results) {
            System.out.println(result.toString());
        }

    }

    @DisplayName("Boolean Builder로 동적 쿼리 만들기")
    @Test
    void dynamicQuery_booleanBuilder() {

        // given
        String usernameCond = "Kim";
        Integer ageCond = 20;

        // when
        BooleanBuilder builder = new BooleanBuilder();

        // BooleanBuilder 에 조건에 대한 내용을 체이닝하여 연결
        if (usernameCond != null) {
            builder.and(member.name.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        List<Member> fetch = query.select(member)
                .from(member)
                .where(builder)     // 위에서 만든 BooleanBuilder 로 조건문 생성
                .fetch();

        // then
        for (Member member : fetch) {
            System.out.println(member.toString());
        }

        Assertions.assertThat(fetch.size()).isEqualTo(1);


    }

    @DisplayName("Where 파라메터로 동적 쿼리 만들기")
    @Test
    void dynamicQuery_whereParam() {
        // given
        String usernameCond = "Kim";
        Integer ageCond = 20;

        // when
//        List<Member> results = query.selectFrom(member)
//                .where(usernameEq(usernameCond), ageEq(ageCond))
//                .fetch();

        // 메소드만 넣었을떄 -> BooleanExpression을 이용하여 or 조건으로 바꾸었을때
        List<Member> results = query.selectFrom(member)
                .where(usernameEq(usernameCond).or(ageEq(ageCond)))
                .fetch();

        // then
        for (Member result : results) {
            System.out.println(result);
        }
        Assertions.assertThat(results).hasSizeLessThanOrEqualTo(1);

    }

    private BooleanExpression usernameEq(String usernameCond) {
        if (usernameCond == null) {
            return null;    // null 을 반환 시에 where에서 해당 조건을 무시하고 넘어간다.
        }
        return member.name.eq(usernameCond);
    }

    private Predicate ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }


}
