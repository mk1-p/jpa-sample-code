package com.example.jpatestcode.members.repository;

import com.example.jpatestcode.members.dto.MemberSearchCondition;
import com.example.jpatestcode.members.dto.PreMemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MemberRepositoryCustom {

    List<PreMemberDto> search(MemberSearchCondition condition);

    Page<PreMemberDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);

    Page<PreMemberDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);

    Page<PreMemberDto> searchPageOptimization(MemberSearchCondition condition, Pageable pageable);

}
