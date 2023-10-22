package com.example.jpatestcode.querydsl;

import com.example.jpatestcode.boards.Board;
import com.example.jpatestcode.boards.QBoard;
import com.example.jpatestcode.boards.QBoardRepository;
import com.example.jpatestcode.members.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
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


}
