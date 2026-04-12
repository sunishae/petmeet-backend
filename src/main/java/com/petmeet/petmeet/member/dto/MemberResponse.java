package com.petmeet.petmeet.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 회원 API의 응답 DTO. 가입/로그인/조회 모두 이 형태로 반환.
// Member 엔티티에는 password, deletedAt 등 민감하거나 불필요한 필드가 있으므로,
// 클라이언트에게 꼭 필요한 필드만 골라서 내려준다.
// → password를 절대 응답에 포함하면 안 된다.
@Getter
@AllArgsConstructor // Service에서 new MemberResponse(id, email, nickname)으로 생성하기 위해 필요
public class MemberResponse {
    private Long memberId;
    private String email;
    private String nickname;
}
