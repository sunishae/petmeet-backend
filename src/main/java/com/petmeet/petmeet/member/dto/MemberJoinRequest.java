package com.petmeet.petmeet.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 회원가입 요청 시 클라이언트가 보내는 데이터를 담는 DTO.
// @NoArgsConstructor: Spring이 JSON 요청 바디를 이 객체로 변환(역직렬화)할 때 파라미터 없는 생성자가 필요.
@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    // @Email: 이메일 형식인지 검증 (xxx@xxx.xxx 형태)
    // @NotBlank: null, 빈 문자열(""), 공백만 있는 문자열(" ") 모두 거부
    // 검증 실패 시 message에 적힌 내용이 GlobalExceptionHandler를 통해 클라이언트에 전달됨
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
    private String nickname;
}
