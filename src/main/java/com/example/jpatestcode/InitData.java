package com.example.jpatestcode;

import com.example.jpatestcode.boards.Board;
import com.example.jpatestcode.boards.BoardRepository;
import com.example.jpatestcode.comments.Comment;
import com.example.jpatestcode.comments.CommentRepository;
import com.example.jpatestcode.info.Info;
import com.example.jpatestcode.info.InfoRepository;
import com.example.jpatestcode.members.Member;
import com.example.jpatestcode.members.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    public final MemberRepository memberRepository;
    public final BoardRepository boardRepository;
    public final CommentRepository commentRepository;
    public final InfoRepository infoRepository;

    @PostConstruct
    @Transactional
    public void saveInitData() {
        log.info("테스트용 초기 데이터 입력");

        // Member Data
        Member john = Member.builder()
                .name("John")
                .age(10)
                .build();
        Member kim = Member.builder()
                .name("Kim")
                .age(20)
                .build();
        Member bob = Member.builder()
                .name("bob")
                .age(30)
                .build();
        List<Member> members = new ArrayList<>();
        members.add(john);
        members.add(kim);
        members.add(bob);

        for (int i = 4; i < 20; i++) {
            Member member = Member.builder()
                    .name("member"+i)
                    .age(i+10)
                    .build();

            members.add(member);
        }

        memberRepository.saveAll(members);



        // Board Data
        Board board1 = Board.builder()
                .title("게시글 1번")
                .content("아무 내용이나 적었다")
                .member(john)
                .build();

        Board board2 = Board.builder()
                .title("게시글 2번")
                .content("귀찮은 개발자")
                .member(john)
                .build();

        Board board3 = Board.builder()
                .title("게시글 3번")
                .content("KIM`s Board!")
                .member(kim)
                .build();

        Board board4 = Board.builder()
                .title("게시글 4번")
                .content("귀찮아졌다...")
                .member(kim)
                .build();

        List<Board> boards = new ArrayList<>();
        boards.add(board1);
        boards.add(board2);
        boards.add(board3);
        boards.add(board4);

        for (int i = 5; i < 20; i++) {
            Board board = Board.builder()
                    .title("게시글 " + i + "번")
                    .content("반복문 게시글 " + i)
                    .member(bob)
                    .build();

            boards.add(board);
        }

        boardRepository.saveAll(boards);


        // Comment Data
        Comment comment1 = Comment.builder()
                .content("댓글 1번을 달았다.")
                .member(kim)
                .board(board1)
                .build();
        Comment comment2 = Comment.builder()
                .content("댓글 2번을 달았다.")
                .member(john)
                .board(board1)
                .build();
        Comment comment3 = Comment.builder()
                .content("댓글 3번을 달았다.")
                .member(kim)
                .board(board1)
                .build();
        Comment comment4 = Comment.builder()
                .content("다른 글에 댓글 테스트1")
                .member(john)
                .board(board3)
                .build();

        board1.getComments().add(comment1);
        board1.getComments().add(comment2);
        board1.getComments().add(comment3);
        board3.getComments().add(comment4);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        comments.add(comment4);

        commentRepository.saveAll(comments);


        // Info Data - JoinColumns 관계에서 연관 Key 를 조회할 때, Lazy Loading을 하는가??
        Info info1 = Info.builder().member(kim).text("test info 1").build();
        Info info2 = Info.builder().member(kim).text("test info 2").build();
        Info info3 = Info.builder().member(kim).text("test info 3").build();
        Info info4 = Info.builder().member(john).text("test info 4").build();

        List<Info> infos = new ArrayList<>();
        infos.add(info1);
        infos.add(info2);
        infos.add(info3);
        infos.add(info4);

        infoRepository.saveAll(infos);


    }

}
