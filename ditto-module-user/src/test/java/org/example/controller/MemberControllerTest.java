package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.UserApplication;
import org.example.domain.Member;
import org.example.dto.request.MemberInfoRequest;
import org.example.dto.request.MemberJoinRequest;
import org.example.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.*;
import static org.example.common.fixtures.MemberFixtures.*;

@WebMvcTest(controllers = MemberController.class)
@ContextConfiguration(classes = UserApplication.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원가입을 요청한다.")
    @Test
    void joinMember() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                        .content(objectMapper.writeValueAsString(memberJoinRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("잘못된 이메일 형식으로 요청한다.")
    @Test
    void malEmail() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest("hello", "HelloWorld123!", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                        .content(objectMapper.writeValueAsString(memberJoinRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String message = result.getResponse().getContentAsString(Charset.forName("UTF-8"));

        assertThat(message).contains("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("잘못된 비밀번호 형식으로 요청한다.")
    @Test
    void malPassword() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123", 파랑_이름, 파랑_프로필, "hello I'm Parang");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                        .content(objectMapper.writeValueAsString(memberJoinRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String message = result.getResponse().getContentAsString(Charset.forName("UTF-8"));

        assertThat(message).contains("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
    }

    @DisplayName("잘못된 이름으로 요청한다.")
    @Test
    void malName() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(파랑_이메일, "HelloWorld123", "", 파랑_프로필, "hello I'm Parang");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                        .content(objectMapper.writeValueAsString(memberJoinRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String message = result.getResponse().getContentAsString(Charset.forName("UTF-8"));

        assertThat(message).contains("이름은 필수 입력 값입니다.");
    }

    @DisplayName("최대 이름 길이를 넘겨 요청한다.")
    @Test
    void tooLongName() throws Exception {
        // given
        Member member = Mockito.mock(Member.class);
        Mockito.when(member.getId()).thenReturn(1L);
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest("안녕하세요구르트아줌마야쿠르트주세요요온아줌마헬로헬로키티셔츠츠츠헤헤헤손흥민", "/pef", "소개 별로 안하고 싶어요");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/api/mypage/1")
                        .header("memberId", member.getId())
                        .content(objectMapper.writeValueAsString(memberInfoRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String message = result.getResponse().getContentAsString(Charset.forName("UTF-8"));

        assertThat(message).contains("이름은 최대 25자까지 가능합니다.");
    }

    @DisplayName("회원 정보 변경을 요청한다.")
    @Test
    void updateMemberInfo() throws Exception {
        // given
        Member member = Mockito.mock(Member.class);
        Mockito.when(member.getId()).thenReturn(1L);
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest("보라돌이", "/pef", "소개 별로 안하고 싶어요");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/mypage/1")
                        .header("memberId", member.getId())
                        .content(objectMapper.writeValueAsString(memberInfoRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}