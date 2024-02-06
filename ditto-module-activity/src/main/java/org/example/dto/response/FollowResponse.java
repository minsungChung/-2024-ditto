package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Member;

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
