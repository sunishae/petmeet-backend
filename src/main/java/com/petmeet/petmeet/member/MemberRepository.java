package com.petmeet.petmeet.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

// 회원 데이터에 접근하는 저장소 클래스.
// 현재는 DB 없이 메모리(ArrayList)에 데이터를 저장한다.
@Repository
public class MemberRepository {

    private final List<Member> members = new ArrayList<>();

    // AtomicLong: 멀티 스레드 환경에서도 안전하게 숫자를 증가시키는 클래스.
    // 회원이 저장될 때마다 1씩 증가해서 고유 ID를 생성한다. (DB의 auto_increment 역할)
    private final AtomicLong idGenerator = new AtomicLong(1);

    // 회원 저장. ID를 자동 생성해서 새 Member 객체를 만들고 리스트에 추가 후 반환.
    // 원본 member의 id는 null이므로, id를 채운 새 객체를 만들어서 저장하는 방식.
    public Member save(Member member) {
        Member newMember = new Member(
                idGenerator.getAndIncrement(), // 현재 값을 반환하고 1 증가 (1, 2, 3, ...)
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                member.getCreatedAt(),
                member.getDeletedAt()
        );
        members.add(newMember);
        return newMember;
    }

    // ID로 회원 조회. 탈퇴한 회원(deletedAt != null)은 결과에서 제외.
    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(m -> m.getId().equals(id))
                .filter(m -> m.getDeletedAt() == null)  // 탈퇴 회원 제외
                .findFirst();
    }

    // 이메일로 회원 조회. 로그인 시 사용. 탈퇴 회원 제외.
    public Optional<Member> findByEmail(String email) {
        return members.stream()
                .filter(m -> m.getEmail().equals(email))
                .filter(m -> m.getDeletedAt() == null)  // 탈퇴 회원 제외
                .findFirst();
    }

    // 이메일 중복 여부 확인. 회원가입 시 같은 이메일로 가입 방지.
    public boolean existsByEmail(String email) {
        return members.stream()
                .anyMatch(m -> m.getEmail().equals(email)
                        && m.getDeletedAt() == null);
    }
}
