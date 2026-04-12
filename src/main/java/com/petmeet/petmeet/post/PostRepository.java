package com.petmeet.petmeet.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

// 게시글 데이터에 접근하는 저장소 클래스.
// 현재는 인메모리 ArrayList 기반. JPA 학습 후 JpaRepository로 교체 예정.
@Repository
public class PostRepository {

    private final List<Post> posts = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // 삭제되지 않은 전체 게시글 목록 반환
    public List<Post> findAll() {
        return posts.stream()
                .filter(p -> p.getDeletedAt() == null)
                .toList();
    }

    // ID로 특정 게시글 조회. 삭제된 글은 없는 것으로 처리.
    public Optional<Post> findById(Long id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .filter(p -> p.getDeletedAt() == null)
                .findFirst();
    }

    // 게시글 저장 (신규 작성 + 수정 + 삭제 모두 이 메서드로 처리).
    //
    // ID가 있는 경우 (수정/삭제):
    //   → 기존 글을 찾아서 교체. JPA의 save()가 하는 동작을 직접 구현한 것.
    // ID가 없는 경우 (신규 작성):
    //   → ID를 자동 생성해서 리스트에 추가.
    public Post save(Post post) {
        if (post.getId() != null) {
            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getId().equals(post.getId())) {
                    posts.set(i, post); // 기존 위치에 새 객체로 교체
                    return post;
                }
            }
        }

        Post newPost = new Post(
                idGenerator.getAndIncrement(),
                post.getMemberId(),
                post.getBreedId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
        posts.add(newPost);
        return newPost;
    }
}
