package com.petmeet.petmeet.comment;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 댓글(Comment) 도메인의 핵심 객체 (Entity).
// 댓글은 특정 게시글(Post)에 종속된다. postId로 어느 글의 댓글인지 구분.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long postId;          // 어떤 게시글의 댓글인지
    private Long memberId;        // 작성자 ID
    private String content;       // 댓글 내용
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; // null이면 정상, 값이 있으면 삭제된 댓글 (Soft Delete)
}
