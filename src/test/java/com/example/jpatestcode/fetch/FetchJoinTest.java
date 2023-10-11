package com.example.jpatestcode.fetch;

import com.example.jpatestcode.boards.Board;
import com.example.jpatestcode.boards.BoardService;
import com.example.jpatestcode.comments.Comment;
import com.example.jpatestcode.members.Member;
import com.example.jpatestcode.members.MemberService;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootTest
@Transactional
public class FetchJoinTest {

    private final MemberService memberService;
    private final BoardService boardService;
    private final EntityManager em;

    @Autowired
    public FetchJoinTest(MemberService memberService, BoardService boardService, EntityManager em) {
        this.memberService = memberService;
        this.boardService = boardService;
        this.em = em;
    }


    @Test
    void 특정_멤버를_조회하고_연관된_게시글을_가져온다_NOT_FETCH() {

        //given
        Long id = 1l;

        //when
        System.out.println("Member의 데이터를 가져온다.");
        Member member = memberService.getMember(id);

        System.out.println("연관된 Board 데이터를 확인한다.");
        List<Board> boards = member.getBoards();
        for (Board board : boards) {
            System.out.println(board.toString());
        }

        //then
        Assertions.assertThat(boards.size()).isSameAs(2);

    }

    @Test
    @Transactional
    void 전체_멤버를_조회하고_연관된_게시글을_가져온다_NOT_FETCH() {

        //given

        //when
        System.out.println("Member의 데이터를 가져온다.");
        List<Member> members = memberService.getAllMembers();

        System.out.println("연관된 Board 데이터를 확인한다.");
        for (Member member : members) {
            List<Board> boards = member.getBoards();
            System.out.println(member.getName() + "의 게시글 수 : "+boards.size());
        }

        //then
        Assertions.assertThat(members.size()).isSameAs(2);

    }

    @Test
    @Transactional
    void 전체_멤버를_조회하고_연관된_게시글을_가져온다_FETCH() {

        //given

        //when
        System.out.println("Member의 데이터를 가져온다.");
        List<Member> members = memberService.getAllMembersWithBoard();

        System.out.println("연관된 Board 데이터를 확인한다.");
        for (Member member : members) {
            List<Board> boards = member.getBoards();
            System.out.println(member.getName() + "의 게시글 수 : "+boards.size());
        }

        //then
        Assertions.assertThat(members.size()).isSameAs(2);

    }

    @Test
    void 패치조인에서_페이징() {


        //given


        //when
        System.out.println("Member의 데이터를 가져온다.");
        List<Member> members = em.createQuery("select m from Member m join fetch m.boards", Member.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();


        System.out.println("연관된 Board 데이터를 확인한다.");
        for (Member member : members) {
            List<Board> boards = member.getBoards();
            System.out.println(member.getName() + "의 게시글 수 : "+boards.size());
        }

        //then
        Assertions.assertThat(members.size()).isSameAs(2);


    }


    @Test
    void Board_전체_Fetch_Join() {

        //given


        //when
        System.out.println("Board를 조회한다.");
        List<Board> boards = boardService.getAllBoardsFetchAll();


        System.out.println("연관 데이터를 확인한다. board size : "+boards.size());
        for (Board board : boards) {
            Member member = board.getMember();
            List<Comment> comments = board.getComments();
            System.out.println("writer : "+member.getName()+" comment size : "+comments.size());
        }


        //then
        Assertions.assertThat(boards.size()).isSameAs(4);


    }

    @Test
    void Board_toOne_패치_toMany_배치_조회_페이징_테스트() {

        //given


        //when
        System.out.println("Board를 조회한다.");

        List<Board> boards = em.createQuery("select b from Board b join fetch b.member", Board.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();


        System.out.println("연관 데이터를 확인한다.");
        for (Board board : boards) {
            Member member = board.getMember();
            List<Comment> comments = board.getComments();
            System.out.println("writer : "+member.getName()+" comment size : "+comments.size());
        }


        //then
        Assertions.assertThat(boards.size()).isSameAs(4);


    }

    @Test
    void Board_toOne_패치_toMany_배치_조회() {

        //given


        //when
        System.out.println("Board를 조회한다.");
        List<Board> boards = boardService.getAllBoardsFetchMember();


        System.out.println("연관 데이터를 확인한다.");
        for (Board board : boards) {
            Member member = board.getMember();
            List<Comment> comments = board.getComments();
            System.out.println("writer : "+member.getName()+" comment size : "+comments.size());
        }


        //then
        Assertions.assertThat(boards.size()).isSameAs(4);


    }



}
