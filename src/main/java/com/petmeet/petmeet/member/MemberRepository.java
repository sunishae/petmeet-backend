package com.petmeet.petmeet.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final List<Member> members = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Member save(Member member) {
        Member newMember = new Member(
                idGenerator.getAndIncrement(),
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                member.getCreatedAt(),
                member.getDeletedAt()
        );
        members.add(newMember);
        return newMember;
    }

    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(m -> m.getId().equals(id))
                .filter(m -> m.getDeletedAt() == null)  // 탈퇴 회원 제외
                .findFirst();
    }

    public Optional<Member> findByEmail(String email) {
        return members.stream()
                .filter(m -> m.getEmail().equals(email))
                .filter(m -> m.getDeletedAt() == null)  // 탈퇴 회원 제외
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return members.stream()
                .anyMatch(m -> m.getEmail().equals(email)
                        && m.getDeletedAt() == null);
    }
}
