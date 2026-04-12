package com.petmeet.petmeet.post.dto;

import com.petmeet.petmeet.comment.dto.CommentResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 게시글 상세 조회 시 반환하는 응답 DTO.
// 목록(PostResponse)보다 더 많은 정보를 포함하며, 댓글 목록도 함께 반환한다.
//
// 여러 도메인의 데이터를 하나의 DTO에 조합:
//   Post    → id, title, content, breedId, createdAt
//   Member  → authorNickname (MemberService 조회)
//   Breed   → breedName (BreedService 조회, nullable)
//   Comment → comments (CommentService 조회)
@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;
    private Long breedId;                  // nullable
    private String breedName;             // nullable
    private List<CommentResponse> comments; // comment 도메인에서 가져온 댓글 목록
    private LocalDateTime createdAt;
}
