package org.example.domain;

import org.example.UserApplication;
import org.example.config.JpaConfig;
import org.example.global.exception.NoSuchMemberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;
import static org.example.common.fixtures.MemberFixtures.파랑;
import static org.example.common.fixtures.MemberFixtures.파랑_이메일;

@SpringBootTest
@ContextConfiguration(classes = UserApplication.class)
@Import(JpaConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown(){
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("중복된 이메일이 존재하는 경우 true를 반환한다.")
    @Test
    void checkDuplicatedEmail(){
        // given
        memberRepository.save(파랑());

        // when & then
        assertThat(memberRepository.existsByEmail(파랑_이메일)).isTrue();
    }

    @DisplayName("이메일을 통해 회원을 찾는다.")
    @Test
    void searchMemberByEmail(){
        // given
        Member 파랑 = memberRepository.save(파랑());

        // when
        Member actual = memberRepository.getByEmail(파랑_이메일);

        // then
        assertThat(actual.getId()).isEqualTo(파랑.getId());
    }

    @DisplayName("존재하지 않는 email을 조회할 경우 예외를 던진다.")
    @Test
    void searchMemberByNotExistingEmail(){
        // given
        String email = "hello@gmail.com";

        // given & when & then
        assertThatThrownBy(() -> memberRepository.getByEmail(email))
                .isInstanceOf(NoSuchMemberException.class);
    }

}