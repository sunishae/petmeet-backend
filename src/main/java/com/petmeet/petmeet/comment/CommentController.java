package com.petmeet.petmeet.comment;

import com.petmeet.petmeet.comment.dto.CommentCreateRequest;
import com.petmeet.petmeet.comment.dto.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 댓글 관련 HTTP 요청을 처리하는 컨트롤러.
//
// URL이 /api/v1/posts/{postId}/comments 형태인 이유:
//   댓글은 게시글에 종속된 리소스다.
//   REST 설계에서는 종속 리소스를 부모 리소스 URL 아래에 중첩시킨다.
//   → /api/v1/posts/3/comments = 3번 게시글의 댓글들
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // POST /api/v1/posts/{postId}/comments → 댓글 작성
    // HTTP 201 Created: 새 리소스가 생성됐을 때 사용하는 상태코드
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(postId, request));
    }

    // DELETE /api/v1/posts/{postId}/comments/{id}?memberId={memberId} → 댓글 삭제
    // ⚠️ Auth(JWT) 학습 후 @RequestParam memberId 제거 예정
    // HTTP 204 No Content: 삭제 성공 시 응답 바디 없이 반환하는 상태코드
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long id,
            @RequestParam Long memberId) {
        commentService.deleteComment(postId, id, memberId);
        return ResponseEntity.noContent().build();
    }
}
