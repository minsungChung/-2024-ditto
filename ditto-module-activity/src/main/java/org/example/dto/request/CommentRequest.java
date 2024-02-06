package org.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @Size(min = 1, max = 300, message = "댓글의 길이는 1자 이상 300자 이하여야합니다.")
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;
}
