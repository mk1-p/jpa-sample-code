package com.example.jpatestcode.fetch;


import com.example.jpatestcode.boards.Board;
import com.example.jpatestcode.info.Info;
import com.example.jpatestcode.info.InfoRepository;
import com.example.jpatestcode.members.Member;
import com.example.jpatestcode.members.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class LazyLoadingTest {


    private final InfoRepository infoRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LazyLoadingTest(InfoRepository infoRepository, MemberRepository memberRepository) {
        this.infoRepository = infoRepository;
        this.memberRepository = memberRepository;
    }


    @Test
    void JoinColumns_관계에서_JoinColumn_값은_LazyLoading_하는가() {

        //given

        //when
        System.out.println("Info 데이터를 가져온다.");
        List<Info> infos = infoRepository.findAll();

        System.out.println("연관된 Member 데이터를 확인한다.");
        for (Info info : infos) {
            Member member = info.getMember();
            System.out.println("member class : "+member.getClass());
            System.out.println("member name : "+member.getName());

            List<Board> boards = member.getBoards();
            for (Board board : boards) {
                System.out.println("board class : "+board.getClass());
                System.out.println("board title : "+board.getTitle());
            }
        }

        // dummy 컬럼을 불러올때 Lazy Loading이 이루어 지는가?
        String dummy = infos.get(0).getMember().getDummy();
        System.out.println("dummy test : "+dummy);

        //then
        Assertions.assertThat(infos.get(0).getMember().getName()).isSameAs("Kim");

    }


}
