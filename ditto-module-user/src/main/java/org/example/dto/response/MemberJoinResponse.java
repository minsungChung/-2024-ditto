package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinResponse {
    private Long id;
    private String email;
    private String memberName;
    private String profileIamge;
    private String bio;

    public MemberJoinResponse(Member member){
        this(member.getId(), member.getEmail(), member.getMemberName(),
                member.getProfileImage(), member.getBio());
    }
}
