package org.example.common.fixtures;

import org.example.domain.Member;

import java.time.LocalDateTime;

public class MemberFixtures {

    /* 파랑 */
    public static final String 파랑_이메일 = "parang@email.com";
    public static final String 파랑_이름 = "파랑";
    public static final String 파랑_프로필 = "/parang.png";

    public static Member 파랑(){
        return Member.builder()
                .bio("hello parang")
                .memberName(파랑_이름)
                .email(파랑_이메일)
                .password("Hello123!")
                .lastLogin(LocalDateTime.now())
                .profileImage(파랑_프로필)
                .build();
    }
}
