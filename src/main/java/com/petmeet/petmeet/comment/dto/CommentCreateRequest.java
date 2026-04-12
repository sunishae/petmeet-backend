package com.petmeet.petmeet.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 댓글 작성 요청 DTO.
@Getter
@NoArgsConstructor
public class CommentCreateRequest {

    // ⚠️ Auth(JWT) 학습 후 제거 예정.
    // 현재 인증이 없으므로 작성자를 요청에서 직접 받는다.
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long memberId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
}
