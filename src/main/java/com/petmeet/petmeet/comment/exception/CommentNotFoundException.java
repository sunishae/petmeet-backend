package com.petmeet.petmeet.comment.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

// 댓글을 찾지 못했을 때 던지는 예외.
// 삭제 시 해당 ID의 댓글이 없거나 이미 삭제된 경우 사용.
public class CommentNotFoundException extends BusinessException {
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
