package com.petmeet.petmeet.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시글 수정 요청 DTO.
// memberId를 포함하는 이유:
//   현재 Auth(JWT)가 없어서 "누가 수정하려는지"를 요청에서 직접 받아야 한다.
//   JWT 학습 후 이 필드는 제거되고, 토큰에서 자동으로 추출하는 방식으로 교체될 예정.
@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    // ⚠️ Auth(JWT) 학습 후 제거 예정
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long memberId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
