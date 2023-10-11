package com.example.jpatestcode.boards;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseGet(null);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> getAllBoardsFetchAll() {
        return boardRepository.findAllFetchMemberAndComments();
    }

    public List<Board> getAllBoardsFetchMember() {
        return boardRepository.findAllFetchMember();
    }

}
