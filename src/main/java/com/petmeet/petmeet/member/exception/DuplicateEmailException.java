package com.petmeet.petmeet.member.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

// 이미 가입된 이메일로 회원가입을 시도할 때 던지는 예외.
// join() 서비스 메서드에서 existsByEmail() 결과가 true일 때 사용.
public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
