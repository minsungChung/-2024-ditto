package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.global.dto.MemberDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowMemberResponse {
    private Long memberId;
    private String profileImage;
    private String memberName;

    public FollowMemberResponse(MemberDto member){
        this(member.getMemberId(), member.getProfileImage(), member.getMemberName());
    }
}
