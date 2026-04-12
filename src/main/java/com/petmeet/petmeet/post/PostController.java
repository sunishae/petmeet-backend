package com.petmeet.petmeet.post;

import com.petmeet.petmeet.post.dto.PostCreateRequest;
import com.petmeet.petmeet.post.dto.PostDetailResponse;
import com.petmeet.petmeet.post.dto.PostResponse;
import com.petmeet.petmeet.post.dto.PostUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 게시글 관련 HTTP 요청을 받아서 응답을 반환하는 컨트롤러.
// Controller의 역할은 요청을 받고 응답을 반환하는 것뿐. 비즈니스 로직은 Service에 위임.
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // GET /api/v1/posts → 게시글 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    // GET /api/v1/posts/{id} → 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    // POST /api/v1/posts → 게시글 작성
    // @RequestBody @Valid: JSON 요청 바디를 DTO로 변환하고, 검증 애노테이션(@NotBlank 등)을 실행
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    // PUT /api/v1/posts/{id} → 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostUpdateRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    // DELETE /api/v1/posts/{id}?memberId={memberId} → 게시글 삭제
    // ⚠️ Auth(JWT) 학습 후 @RequestParam memberId는 제거 예정.
    // 현재는 인증이 없으므로 누가 삭제하려는지를 쿼리 파라미터로 직접 받는다.
    // 예: DELETE /api/v1/posts/1?memberId=3
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestParam Long memberId) {
        postService.deletePost(id, memberId);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content — 삭제 성공 시 바디 없이 반환
    }
}
