package com.example.jpatestcode.boards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("select b from Board b " +
            "join fetch b.member " +
            "left join fetch b.comments")
    List<Board> findAllFetchMemberAndComments();    // 게시글 - Member, Comment fetch join

    @Query("select b from Board b " +
            "join fetch b.member")
    List<Board> findAllFetchMember();               // 게시글 - Member fetch join

}
