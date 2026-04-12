package com.petmeet.petmeet.member.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

// 회원을 찾지 못했을 때 던지는 예외.
// getMember(id) 호출 시 해당 ID의 회원이 없을 때 사용.
public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
