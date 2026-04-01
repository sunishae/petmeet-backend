package com.petmeet.petmeet.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 회원
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),

    // 품종
    BREED_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 품종입니다."),

    // 게시글
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    FORBIDDEN_POST(HttpStatus.FORBIDDEN, "게시글 수정/삭제 권한이 없습니다."),

    // 댓글
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),

    // 설문
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND, "설문 내역이 없습니다."),

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getMessage() { return message; }
}
