package com.petmeet.petmeet.post.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

// 본인이 작성하지 않은 글을 수정/삭제하려 할 때 던지는 예외.
// Service에서 요청자의 memberId와 글의 memberId를 비교해서 다르면 던진다.
// Auth(JWT) 학습 후에는 토큰에서 memberId를 추출해서 검증하는 방식으로 교체될 예정.
public class ForbiddenPostException extends BusinessException {
    public ForbiddenPostException() {
        super(ErrorCode.FORBIDDEN_POST);
    }
}
