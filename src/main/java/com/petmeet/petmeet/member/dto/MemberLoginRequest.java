package com.petmeet.petmeet.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 로그인 요청 시 클라이언트가 보내는 데이터를 담는 DTO.
// 가입 요청(MemberJoinRequest)과 별도 클래스로 분리한 이유:
//   → 로그인에는 닉네임, 비밀번호 길이 검증 등이 불필요하므로 각자의 책임에 맞게 분리.
@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
