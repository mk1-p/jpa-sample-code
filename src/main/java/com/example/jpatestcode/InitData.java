package com.example.jpatestcode;

import com.example.jpatestcode.boards.Board;
import com.example.jpatestcode.boards.BoardRepository;
import com.example.jpatestcode.members.Member;
import com.example.jpatestcode.members.MemberRepository;
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

    @PostConstruct
    @Transactional
    public void saveInitData() {
        log.info("테스트용 초기 데이터 입력");

        // Member Data
        Member john = Member.builder()
                .name("John")
                .build();
        Member kim = Member.builder()
                .name("Kim")
                .build();
        List<Member> members = new ArrayList<>();
        members.add(john);
        members.add(kim);

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

        boardRepository.saveAll(boards);

    }

}
