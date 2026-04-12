package com.petmeet.petmeet.post;

import com.petmeet.petmeet.breed.BreedService;
import com.petmeet.petmeet.comment.CommentService;
import com.petmeet.petmeet.comment.dto.CommentResponse;
import com.petmeet.petmeet.member.MemberService;
import com.petmeet.petmeet.post.dto.PostCreateRequest;
import com.petmeet.petmeet.post.dto.PostDetailResponse;
import com.petmeet.petmeet.post.dto.PostResponse;
import com.petmeet.petmeet.post.dto.PostUpdateRequest;
import com.petmeet.petmeet.post.exception.ForbiddenPostException;
import com.petmeet.petmeet.post.exception.PostNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

// 게시글 도메인의 비즈니스 로직을 담당하는 클래스.
//
// ⚠️ @Lazy 사용 이유:
//   PostService → CommentService (댓글 목록 조회)
//   CommentService → PostRepository (게시글 존재 검증)
//
//   만약 CommentService가 PostService를 의존하면 A→B→A 순환 의존성이 생긴다.
//   현재는 CommentService가 PostRepository를 직접 사용해 순환을 끊었다.
//   @Lazy는 CommentService 빈을 즉시 생성하지 않고, 실제 호출 시점에 생성하도록 지연시킨다.
//   Spring에서 순환 의존성을 해결하는 대표적인 패턴이다.
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final BreedService breedService;

    // @Lazy: CommentService가 PostRepository를 주입받기 때문에 순환 의존성 방지용
    private final @Lazy CommentService commentService;

    // 전체 게시글 목록 조회
    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream()
                .map(post -> {
                    // 작성자 닉네임 조회 — 현재는 인메모리라 성능 문제 없음
                    // JPA 도입 후에는 JOIN으로 한 번에 처리하는 방식으로 개선 예정
                    String authorNickname = memberService.getMember(post.getMemberId()).getNickname();
                    String breedName = resolveBreedName(post.getBreedId());

                    return new PostResponse(
                            post.getId(),
                            post.getTitle(),
                            authorNickname,
                            post.getBreedId(),
                            breedName,
                            post.getCreatedAt()
                    );
                })
                .toList();
    }

    // 게시글 상세 조회 — 댓글 목록도 함께 반환
    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        String authorNickname = memberService.getMember(post.getMemberId()).getNickname();
        String breedName = resolveBreedName(post.getBreedId());

        // CommentService에서 해당 게시글의 댓글 목록 조회
        List<CommentResponse> comments = commentService.getCommentsByPostId(id);

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                authorNickname,
                post.getBreedId(),
                breedName,
                comments,
                post.getCreatedAt()
        );
    }

    // 게시글 작성
    public PostResponse createPost(PostCreateRequest request) {
        String authorNickname = memberService.getMember(request.getMemberId()).getNickname();
        String breedName = resolveBreedName(request.getBreedId());

        Post post = new Post(
                null,
                request.getMemberId(),
                request.getBreedId(),
                request.getTitle(),
                request.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        Post saved = postRepository.save(post);
        return new PostResponse(
                saved.getId(),
                saved.getTitle(),
                authorNickname,
                saved.getBreedId(),
                breedName,
                saved.getCreatedAt()
        );
    }

    // 게시글 수정 — 요청자와 작성자가 다르면 ForbiddenPostException 발생
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        // 요청자(request.getMemberId())와 글 작성자(post.getMemberId())가 다르면 권한 없음
        if (!post.getMemberId().equals(request.getMemberId())) {
            throw new ForbiddenPostException();
        }

        // Post는 불변 객체(setter 없음)이므로, 수정된 값으로 새 객체를 만들어 덮어씌운다.
        // JPA 학습 후 @Transactional + 더티 체킹 방식으로 교체될 예정.
        Post updated = new Post(
                post.getId(),
                post.getMemberId(),
                post.getBreedId(),
                request.getTitle(),
                request.getContent(),
                post.getCreatedAt(),
                LocalDateTime.now(), // updatedAt 갱신
                null
        );

        Post saved = postRepository.save(updated);
        String authorNickname = memberService.getMember(saved.getMemberId()).getNickname();
        String breedName = resolveBreedName(saved.getBreedId());

        return new PostResponse(saved.getId(), saved.getTitle(), authorNickname, saved.getBreedId(), breedName, saved.getCreatedAt());
    }

    // 게시글 삭제 (Soft Delete)
    public void deletePost(Long id, Long memberId) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getMemberId().equals(memberId)) {
            throw new ForbiddenPostException();
        }

        Post deleted = new Post(
                post.getId(),
                post.getMemberId(),
                post.getBreedId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                LocalDateTime.now() // deletedAt 세팅 → 이 글은 이제 조회 불가
        );

        postRepository.save(deleted);
    }

    // breedId가 null이면 null, 있으면 BreedService에서 이름을 조회해 반환하는 헬퍼 메서드.
    private String resolveBreedName(Long breedId) {
        if (breedId == null) return null;
        return breedService.getBreed(breedId).getName();
    }
}
