package org.example.service;

import org.example.UserApplication;
import org.example.domain.MemberAuthenticationCodeEntity;
import org.example.domain.MemberAuthenticationCodeRepository;
import org.example.domain.MemberRepository;

import org.example.dto.request.MemberInfoRequest;
import org.example.dto.request.MemberJoinRequest;
import org.example.dto.request.MemberPasswordRequest;
import org.example.dto.response.MemberJoinResponse;
import org.example.dto.response.UpdateMemberResponse;
import org.example.global.exception.InvalidAccessException;
import org.example.global.exception.InvalidEmailException;
import org.example.global.exception.SamePasswordException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.common.fixtures.MemberFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration(classes = UserApplication.class)
class MemberServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberAuthenticationCodeRepository codeRepository;


    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        codeRepository.deleteAllInBatch();
    }

    @DisplayName("인증되지 않은 메일로 회원 가입을 진행한다.")
    @Test
    void 인증되지_않은_이메일_회원_가입() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");

        // when & then
        assertThatThrownBy(() -> memberService.saveMember(memberJoinRequest))
                .isInstanceOf(InvalidEmailException.class);
    }

    @DisplayName("회원 가입을 진행한다.")
    @Test
    void 회원_가입() {
        // given
        MemberAuthenticationCodeEntity code = new MemberAuthenticationCodeEntity(파랑_이메일, "abcdef");
        code.authenticateEmail();
        codeRepository.save(code);
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");

        // when
        MemberJoinResponse memberJoinResponse = memberService.saveMember(memberJoinRequest);

        // then
        assertThat(memberJoinResponse.getEmail()).isEqualTo(memberJoinRequest.getEmail());
    }

    @DisplayName("이미 존재하는 이메일로 회원 가입을 진행한다.")
    @Test
    void 존재하는_이메일_회원_가입() {
        // given
        MemberAuthenticationCodeEntity code = new MemberAuthenticationCodeEntity(파랑_이메일, "abcdef");
        code.authenticateEmail();
        codeRepository.save(code);
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        memberService.saveMember(memberJoinRequest);
        MemberJoinRequest memberJoinRequest2 = new MemberJoinRequest(파랑_이메일, "kakaka123!", "jjap", "/hello.png", "나는 파랑이 아니다.");

        // when & then
        assertThatThrownBy(() -> memberService.saveMember(memberJoinRequest2))
                .isInstanceOf(InvalidEmailException.class);
    }

    @DisplayName("멤버 정보를 업데이트한다.")
    @Test
    void 멤버_정보_업데이트() {
        // given
        MemberAuthenticationCodeEntity code = new MemberAuthenticationCodeEntity(파랑_이메일, "abcdef");
        code.authenticateEmail();
        codeRepository.save(code);
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        Long memberId = memberService.saveMember(memberJoinRequest).getId();
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest("보라돌이", "/pef", "소개 별로 안하고 싶어요");

        // when
        UpdateMemberResponse updateMemberResponse = memberService.updateMemberInfo(memberId, memberId, memberInfoRequest);

        // then
        assertThat(updateMemberResponse.getMemberId()).isEqualTo(memberId);
    }

    @DisplayName("다른 인가 정보로 업데이트한다.")
    @Test
    void 다른_인가_정보_업데이트() {
        // given
        MemberAuthenticationCodeEntity code = new MemberAuthenticationCodeEntity(파랑_이메일, "abcdef");
        MemberAuthenticationCodeEntity code2 = new MemberAuthenticationCodeEntity("min@gmail.com", "abseff");
        code.authenticateEmail();
        code2.authenticateEmail();
        codeRepository.saveAll(List.of(code, code2));
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        Long memberId = memberService.saveMember(memberJoinRequest).getId();
        MemberJoinRequest memberJoinRequest2 = new MemberJoinRequest("min@gmail.com", "Hello123!", "dfef", "efef", "helloya");
        Long memberId2 = memberService.saveMember(memberJoinRequest2).getId();
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest("보라돌이", "/pef", "소개 별로 안하고 싶어요");

        // when & then
        assertThatThrownBy(() -> memberService.updateMemberInfo(memberId, memberId2, memberInfoRequest))
                .isInstanceOf(InvalidAccessException.class);
    }

    @DisplayName("멤버 비밀번호를 업데이트한다.")
    @Test
    void 멤버_비밀번호_업데이트() {
        // given
        MemberAuthenticationCodeEntity code = new MemberAuthenticationCodeEntity(파랑_이메일, "abcdef");
        code.authenticateEmail();
        codeRepository.save(code);
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        Long memberId = memberService.saveMember(memberJoinRequest).getId();
        MemberPasswordRequest memberPasswordRequest = new MemberPasswordRequest("Hello123!");

        // when
        UpdateMemberResponse updateMemberResponse = memberService.updateMemberPassword(memberId, memberId, memberPasswordRequest);

        // then
        assertThat(updateMemberResponse.getMemberId()).isEqualTo(memberId);
    }

    @DisplayName("기존 비밀번호와 같은 비밀번호를 업데이트한다.")
    @Test
    void 같은_비밀번호_업데이트() {
        // given
        MemberAuthenticationCodeEntity code = new MemberAuthenticationCodeEntity(파랑_이메일, "abcdef");
        code.authenticateEmail();
        codeRepository.save(code);
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        Long memberId = memberService.saveMember(memberJoinRequest).getId();
        MemberPasswordRequest memberPasswordRequest = new MemberPasswordRequest("HelloWorld123!");

        // when & then
        assertThatThrownBy(() -> memberService.updateMemberPassword(memberId, memberId, memberPasswordRequest))
                .isInstanceOf(SamePasswordException.class);
    }

    @DisplayName("다른 인가 정보로 비밀번호를 업데이트한다.")
    @Test
    void 다른_인가_정보_비밀번호_업데이트() {
        // given
        MemberAuthenticationCodeEntity code = new MemberAuthenticationCodeEntity(파랑_이메일, "abcdef");
        MemberAuthenticationCodeEntity code2 = new MemberAuthenticationCodeEntity("min@gmail.com", "abseff");
        code.authenticateEmail();
        code2.authenticateEmail();
        codeRepository.saveAll(List.of(code, code2));
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        Long memberId = memberService.saveMember(memberJoinRequest).getId();
        MemberJoinRequest memberJoinRequest2 = new MemberJoinRequest("min@gmail.com", "Hello123!", "dfef", "efef", "helloya");
        Long memberId2 = memberService.saveMember(memberJoinRequest2).getId();
        MemberPasswordRequest memberPasswordRequest = new MemberPasswordRequest("HelloWorld123!");

        // when & then
        assertThatThrownBy(() -> memberService.updateMemberPassword(memberId, memberId2, memberPasswordRequest))
                .isInstanceOf(InvalidAccessException.class);
    }
}