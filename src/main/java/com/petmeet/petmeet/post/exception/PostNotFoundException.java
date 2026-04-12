package com.petmeet.petmeet.post.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

// 게시글을 찾지 못했을 때 던지는 예외.
// 상세 조회, 수정, 삭제 시 해당 ID의 글이 없으면 사용.
public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
