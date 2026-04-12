package com.petmeet.petmeet.comment;

import com.petmeet.petmeet.comment.dto.CommentCreateRequest;
import com.petmeet.petmeet.comment.dto.CommentResponse;
import com.petmeet.petmeet.comment.exception.CommentNotFoundException;
import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;
import com.petmeet.petmeet.member.MemberService;
import com.petmeet.petmeet.post.PostRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 댓글 도메인의 비즈니스 로직을 담당하는 클래스.
//
// ⚠️ 설계 참고:
//   CommentService는 게시글 존재 여부를 확인하기 위해 PostRepository를 직접 주입받는다.
//   원칙상 도메인 간 참조는 Service를 통해야 하지만, 여기서는 순환 의존성 문제가 있어 예외적으로 허용:
//
//   - CommentService → PostService (게시글 검증)
//   - PostService → CommentService (댓글 목록 조회)
//
//   위 두 의존성이 동시에 존재하면 A→B→A 순환 의존성이 발생한다.
//   이 문제는 JPA 도입 후 CommentService를 PostService에서 @Lazy로 주입하거나,
//   별도 Facade 클래스를 도입하는 방식으로 해결할 예정.
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostRepository postRepository; // ⚠️ 순환 의존성 방지를 위해 Repository 직접 주입 (JPA 전환 시 재설계 예정)

    // 특정 게시글의 댓글 목록 조회.
    // PostService.getPost()에서 게시글 상세 조회 시 댓글 목록을 채우기 위해 호출한다.
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(comment -> {
                    String authorNickname = memberService.getMember(comment.getMemberId()).getNickname();
                    return new CommentResponse(comment.getId(), authorNickname, comment.getContent(), comment.getCreatedAt());
                })
                .toList();
    }

    // 댓글 작성
    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        // 해당 게시글이 존재하는지 확인. 없으면 POST_NOT_FOUND 예외 발생.
        postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        // 작성자가 실제 존재하는 회원인지 검증
        String authorNickname = memberService.getMember(request.getMemberId()).getNickname();

        Comment comment = new Comment(
                null,
                postId,
                request.getMemberId(),
                request.getContent(),
                LocalDateTime.now(),
                null
        );

        Comment saved = commentRepository.save(comment);
        return new CommentResponse(saved.getId(), authorNickname, saved.getContent(), saved.getCreatedAt());
    }

    // 댓글 삭제 (Soft Delete)
    public void deleteComment(Long postId, Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        // 해당 댓글이 요청한 게시글에 속해 있는지 확인
        // → URL 조작으로 다른 글의 댓글을 삭제하려는 시도 방지
        if (!comment.getPostId().equals(postId)) {
            throw new CommentNotFoundException();
        }

        // 작성자 본인만 삭제 가능
        if (!comment.getMemberId().equals(memberId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_POST); // 댓글 전용 ErrorCode는 추후 추가 가능
        }

        Comment deleted = new Comment(
                comment.getId(),
                comment.getPostId(),
                comment.getMemberId(),
                comment.getContent(),
                comment.getCreatedAt(),
                LocalDateTime.now() // deletedAt 세팅 → 이후 조회 불가
        );

        commentRepository.save(deleted);
    }
}
