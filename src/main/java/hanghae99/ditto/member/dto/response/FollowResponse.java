package hanghae99.ditto.member.dto.response;

import hanghae99.ditto.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {
    private Long fromMemberId;
    private Long toMemberId;

    public FollowResponse(Member fromMember, Member toMember){
        this(fromMember.getId(), toMember.getId());
    }
}
