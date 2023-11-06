package com.example.jpatestcode.members;

import com.example.jpatestcode.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseGet(() -> null);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> getAllMembersWithBoard() {
        return memberRepository.findAllFetchBoard();
    }

}
