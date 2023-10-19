package com.example.jpatestcode.boards;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jpatestcode.boards.QBoard.*;

@Repository
@RequiredArgsConstructor
public class QBoardRepository {

    private final EntityManager em;
    private JPAQueryFactory query;

    @PostConstruct
    protected void initQueryFactory() {
        query = new JPAQueryFactory(em);
    }

    public List<Board> findAllBoard() {

        List<Board> results = query.selectFrom(board).fetch();

        return results;

    }

    public Board findBoardById(Long id) {
        return query.selectFrom(board).where(board.id.eq(id)).fetchOne();

    }


}
