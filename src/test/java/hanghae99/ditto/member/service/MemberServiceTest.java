package hanghae99.ditto.member.service;

import hanghae99.ditto.member.domain.Member;
import hanghae99.ditto.member.domain.MemberRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;
    @Test
    void saveMember() {
        Member member = new Member("als@naver.com", "sdf", "minsung", "dfe", "dfe", LocalDateTime.now());

        Member res = memberService.saveMember(member);

        System.out.println("-------------------");
        System.out.println(res.getCreateDate());
        System.out.println(res.getStatus());
    }
}