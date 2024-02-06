package org.example.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.global.entity.UsageStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Long memberId;

    private String email;

    private String memberName;

    private String profileImage;

    private String bio;

    private UsageStatus status;
}
