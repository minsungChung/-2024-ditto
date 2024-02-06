package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 1, max = 25, message = "이름은 최대 25자까지 가능합니다.")
    private String memberName;

    @Size(min = 1, max = 300)
    private String profileImage;

    @Size(min = 1, max = 100, message = "소개글은 최대 100자까지 가능합니다.")
    private String bio;
}
