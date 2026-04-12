package com.petmeet.petmeet.member.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

// 로그인 시 이메일이 존재하지 않거나, 비밀번호가 틀렸을 때 던지는 예외.
// 두 케이스 모두 같은 예외를 사용하는 이유:
//   "이메일이 없습니다" vs "비밀번호가 틀립니다"를 구분하면
//   공격자가 어떤 이메일이 가입되어 있는지 알 수 있기 때문 (계정 열거 공격 방지).
public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
