package com.petmeet.petmeet.post.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 게시글 상세 조회 응답(PostDetailResponse) 안에 중첩되는 댓글 DTO.
// Comment 도메인이 구현되면 CommentService에서 데이터를 채워줄 예정.
// 현재는 PostService에서 List.of() (빈 리스트)로 반환.
@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String authorNickname; // 댓글 작성자 닉네임 (MemberService에서 조회)
    private String content;
    private LocalDateTime createdAt;
}
