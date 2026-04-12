package com.petmeet.petmeet.post.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 게시글 목록 조회 시 반환하는 응답 DTO.
// 목록에서는 본문(content)을 제외하고 핵심 정보만 내려준다.
// → 목록 화면에서 모든 글의 본문까지 내려주면 불필요한 데이터 전송이 발생하기 때문.
@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String authorNickname; // 작성자 닉네임 (MemberService에서 조회)
    private Long breedId;          // nullable
    private String breedName;      // nullable (breedId가 없으면 null)
    private LocalDateTime createdAt;
}
