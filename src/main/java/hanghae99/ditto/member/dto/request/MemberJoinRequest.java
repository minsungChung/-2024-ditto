package hanghae99.ditto.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequest {
    private String email;
    private String password;
    private String memberName;
    private String profileImage;
    private String bio;
}
