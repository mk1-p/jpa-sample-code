package com.example.jpatestcode.members;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {

    @Query("select m from Member m join fetch m.boards")
    List<Member> findAllFetchBoard();

}
