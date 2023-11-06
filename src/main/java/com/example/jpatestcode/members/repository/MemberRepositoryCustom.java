package com.example.jpatestcode.members.repository;

import com.example.jpatestcode.members.dto.MemberSearchCondition;
import com.example.jpatestcode.members.dto.PreMemberDto;

import java.util.List;


public interface MemberRepositoryCustom {

    List<PreMemberDto> search(MemberSearchCondition condition);

}
