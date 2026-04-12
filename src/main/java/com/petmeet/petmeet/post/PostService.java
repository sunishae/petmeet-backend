package com.petmeet.petmeet.post;

import com.petmeet.petmeet.breed.BreedService;
import com.petmeet.petmeet.member.MemberService;
import com.petmeet.petmeet.post.dto.CommentResponse;
import com.petmeet.petmeet.post.dto.PostCreateRequest;
import com.petmeet.petmeet.post.dto.PostDetailResponse;
import com.petmeet.petmeet.post.dto.PostResponse;
import com.petmeet.petmeet.post.dto.PostUpdateRequest;
import com.petmeet.petmeet.post.exception.ForbiddenPostException;
import com.petmeet.petmeet.post.exception.PostNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 게시글 도메인의 비즈니스 로직을 담당하는 클래스.
//
// 다른 도메인 Service를 주입받는 이유:
//   게시글 응답에 작성자 닉네임(Member)과 품종 이름(Breed)이 필요하기 때문.
//   이때 MemberRepository, BreedRepository를 직접 참조하면 안 되고,
//   반드시 해당 도메인의 Service를 통해서만 접근해야 한다. (아키텍처 원칙)
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;  // 작성자 닉네임 조회용
    private final BreedService breedService;    // 품종 이름 조회용

    // 전체 게시글 목록 조회
    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream()
                .map(post -> {
                    // 작성자 닉네임 조회 — 게시글마다 MemberService를 호출함.
                    // 현재는 인메모리라 성능 문제 없지만, JPA 도입 후에는 JOIN으로 한 번에 처리하는 방식으로 개선 예정.
                    String authorNickname = memberService.getMember(post.getMemberId()).getNickname();

                    // 품종 이름 조회 — breedId가 없으면(null) 품종 무관 글이므로 null 반환
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

    // 게시글 상세 조회
    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        String authorNickname = memberService.getMember(post.getMemberId()).getNickname();
        String breedName = resolveBreedName(post.getBreedId());

        // Comment 도메인 미구현 상태 — 구현 후 CommentService에서 데이터를 채울 예정
        List<CommentResponse> comments = List.of();

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
        // 작성자가 실제 존재하는 회원인지 검증. 없으면 MemberNotFoundException 발생.
        String authorNickname = memberService.getMember(request.getMemberId()).getNickname();

        // breedId가 있으면 실제 존재하는 품종인지 검증. 없으면 BreedNotFoundException 발생.
        String breedName = resolveBreedName(request.getBreedId());

        Post post = new Post(
                null,                   // ID는 Repository에서 자동 생성
                request.getMemberId(),
                request.getBreedId(),
                request.getTitle(),
                request.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null                    // 삭제 안 된 상태
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

    // 게시글 수정
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        // 요청자(request.getMemberId())와 글 작성자(post.getMemberId())가 다르면 권한 없음
        if (!post.getMemberId().equals(request.getMemberId())) {
            throw new ForbiddenPostException();
        }

        // Post는 불변 객체(setter 없음)이므로, 수정된 값으로 새 객체를 만들어서 덮어씌운다.
        // 이 패턴은 JPA 학습 후 @Transactional + 더티 체킹 방식으로 교체될 예정.
        Post updated = new Post(
                post.getId(),           // 기존 ID 유지 (save()에서 교체 트리거)
                post.getMemberId(),
                post.getBreedId(),
                request.getTitle(),     // 새 제목
                request.getContent(),   // 새 내용
                post.getCreatedAt(),
                LocalDateTime.now(),    // updatedAt 갱신
                null
        );

        Post saved = postRepository.save(updated);
        String authorNickname = memberService.getMember(saved.getMemberId()).getNickname();
        String breedName = resolveBreedName(saved.getBreedId());

        return new PostResponse(
                saved.getId(),
                saved.getTitle(),
                authorNickname,
                saved.getBreedId(),
                breedName,
                saved.getCreatedAt()
        );
    }

    // 게시글 삭제 (Soft Delete)
    // 실제로 데이터를 지우지 않고 deletedAt에 현재 시각을 세팅.
    // → 데이터 복구 가능, 탈퇴 회원 글도 보존 가능
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
                LocalDateTime.now()  // deletedAt 세팅 → 이 글은 이제 조회 불가
        );

        postRepository.save(deleted);
    }

    // breedId가 null이면 null을, 있으면 BreedService에서 이름을 조회해서 반환하는 헬퍼 메서드.
    // 여러 메서드에서 반복되는 로직을 하나로 모은 것.
    private String resolveBreedName(Long breedId) {
        if (breedId == null) return null;
        return breedService.getBreed(breedId).getName();
    }
}
