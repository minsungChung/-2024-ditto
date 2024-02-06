package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {

    @Size(min = 1, max = 300, message = "토큰의 길이가 적절하지 않습니다.")
    @NotBlank(message = "토큰은 필수 입력값입니다.")
    private String token;
}
