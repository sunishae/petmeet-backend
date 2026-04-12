package com.petmeet.petmeet.comment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 댓글 응답 DTO.
// 댓글 작성 응답 및 게시글 상세 조회(PostDetailResponse) 내 댓글 목록에서 사용.
@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String authorNickname; // MemberService에서 조회한 작성자 닉네임
    private String content;
    private LocalDateTime createdAt;
}
