package com.petmeet.petmeet.member;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 회원(Member) 도메인의 핵심 객체 (Entity).
// Breed와 동일하게 현재는 순수 POJO, JPA 학습 후 @Entity 애노테이션이 붙을 예정.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 빈 객체 생성 방지
@AllArgsConstructor
public class Member {
    private Long id;
    private String email;
    private String password;          // 현재는 평문 저장. Auth 학습 후 BCrypt 해싱 적용 예정.
    private String nickname;
    private LocalDateTime createdAt;  // 가입 일시
    private LocalDateTime deletedAt;  // 탈퇴 일시. null이면 활성 회원, 값이 있으면 탈퇴한 회원.
                                      // DB에서 실제로 행을 지우지 않고 이 필드로 탈퇴 여부를 관리하는 방식 = 소프트 딜리트(Soft Delete)
}
