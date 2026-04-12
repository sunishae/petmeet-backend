package com.petmeet.petmeet.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 게시글 상세 조회 시 반환하는 응답 DTO.
// 목록(PostResponse)보다 더 많은 정보를 포함한다.
//
// 여러 도메인의 데이터를 하나의 DTO에 조합:
//   Post      → id, title, content, breedId, createdAt
//   Member    → authorNickname (memberId로 MemberService 조회)
//   Breed     → breedName (breedId로 BreedService 조회, nullable)
//   Comment   → comments (Comment 도메인 구현 전까지 빈 리스트 반환)
@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String authorNickname;         // 작성자 닉네임
    private Long breedId;                  // nullable
    private String breedName;             // nullable
    private List<CommentResponse> comments; // Comment 도메인 구현 전까지 빈 리스트
    private LocalDateTime createdAt;
}
