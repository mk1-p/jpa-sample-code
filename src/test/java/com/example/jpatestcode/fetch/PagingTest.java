package com.example.jpatestcode.fetch;

import com.example.jpatestcode.boards.Board;
import com.example.jpatestcode.boards.BoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PagingTest {

    private final BoardService boardService;

    @Autowired
    public PagingTest(BoardService boardService) {
        this.boardService = boardService;
    }


    @Test
    void 게시글_페이징_테스트() {
        //given
        PageRequest request = PageRequest.of(0, 10);

        //when
        Page<Board> boardsPaging = boardService.getBoardsPaging(request);

        //then
        for (Board board : boardsPaging) {
            System.out.println(board.toString());
        }

        Assertions.assertThat(boardsPaging.getSize()).isEqualTo(10);

    }


}
