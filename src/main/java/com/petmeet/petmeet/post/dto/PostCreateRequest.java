package com.petmeet.petmeet.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시글 작성 요청 DTO.
// 수정 요청(PostUpdateRequest)과 분리한 이유:
//   - 작성 시에는 memberId(작성자)가 필요하지만, 수정 시에는 이미 글에 저장되어 있어 불필요
//   - 두 요청의 검증 규칙이 다를 수 있어 분리해두면 나중에 유연하게 대응 가능
@Getter
@NoArgsConstructor
public class PostCreateRequest {

    // ⚠️ Auth(JWT) 학습 후 이 필드는 제거 예정.
    // JWT가 생기면 토큰에서 memberId를 추출하므로 클라이언트가 직접 보낼 필요가 없어진다.
    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long memberId;

    // breedId는 nullable — 품종과 무관한 자유 게시글 허용
    private Long breedId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
