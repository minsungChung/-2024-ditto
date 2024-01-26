package hanghae99.ditto.member.dto.response;

import hanghae99.ditto.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowMemberResponse {
    private Long memberId;
    private String profileImage;
    private String memberName;

    public FollowMemberResponse(Member member){
        this(member.getId(), member.getProfileImage(), member.getMemberName());
    }
}
