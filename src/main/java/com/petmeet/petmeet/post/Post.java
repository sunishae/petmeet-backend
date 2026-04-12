package com.petmeet.petmeet.post;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시글(Post) 도메인의 핵심 객체 (Entity).
// 현재는 순수 POJO, JPA 학습 후 @Entity 애노테이션이 붙을 예정.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {
    private Long id;
    private Long memberId;        // 작성자 ID (Member 참조)
    private Long breedId;         // 관련 품종 ID (nullable — 품종 무관 글 허용)
    private String title;         // 제목 (최대 100자)
    private String content;       // 본문
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;  // 수정할 때마다 갱신
    private LocalDateTime deletedAt;  // null이면 정상 글, 값이 있으면 삭제된 글 (Soft Delete)
}
