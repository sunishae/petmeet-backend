package com.petmeet.petmeet.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

// 댓글 데이터에 접근하는 저장소 클래스.
// 현재는 인메모리 ArrayList 기반. JPA 학습 후 JpaRepository로 교체 예정.
@Repository
public class CommentRepository {

    private final List<Comment> comments = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // 특정 게시글의 삭제되지 않은 댓글 목록 반환.
    // 게시글 상세 조회 시 PostService가 이 메서드를 호출해 댓글 목록을 채운다.
    public List<Comment> findByPostId(Long postId) {
        return comments.stream()
                .filter(c -> c.getPostId().equals(postId))
                .filter(c -> c.getDeletedAt() == null)
                .toList();
    }

    // ID로 특정 댓글 조회. 삭제된 댓글은 없는 것으로 처리.
    public Optional<Comment> findById(Long id) {
        return comments.stream()
                .filter(c -> c.getId().equals(id))
                .filter(c -> c.getDeletedAt() == null)
                .findFirst();
    }

    // 댓글 저장 (신규 + 삭제 모두 이 메서드로 처리).
    // ID가 있으면 기존 댓글 교체 (삭제 시 deletedAt 세팅 용도),
    // ID가 없으면 새 댓글 추가.
    public Comment save(Comment comment) {
        if (comment.getId() != null) {
            for (int i = 0; i < comments.size(); i++) {
                if (comments.get(i).getId().equals(comment.getId())) {
                    comments.set(i, comment);
                    return comment;
                }
            }
        }

        Comment newComment = new Comment(
                idGenerator.getAndIncrement(),
                comment.getPostId(),
                comment.getMemberId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getDeletedAt()
        );
        comments.add(newComment);
        return newComment;
    }
}
