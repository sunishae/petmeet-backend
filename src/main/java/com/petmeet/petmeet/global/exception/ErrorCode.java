package com.petmeet.petmeet.global.exception;

import org.springframework.http.HttpStatus;

// 프로젝트에서 발생할 수 있는 모든 에러 코드를 한 곳에 모아둔 enum.
// 에러 코드를 enum으로 관리하면:
//   1. 어떤 에러가 있는지 한눈에 파악 가능
//   2. 에러 메시지를 코드 전체에서 일관성 있게 사용 가능
//   3. 새 에러 추가 시 이 파일만 수정하면 됨
public enum ErrorCode {

    // ── 회원 ──────────────────────────────────────────
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),

    // ── 품종 ──────────────────────────────────────────
    BREED_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 품종입니다."),

    // ── 게시글 ────────────────────────────────────────
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    FORBIDDEN_POST(HttpStatus.FORBIDDEN, "게시글 수정/삭제 권한이 없습니다."),

    // ── 댓글 ──────────────────────────────────────────
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),

    // ── 설문 ──────────────────────────────────────────
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND, "설문 내역이 없습니다."),

    // ── 공통 ──────────────────────────────────────────
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    // 각 에러 코드가 가지는 HTTP 상태코드와 사용자에게 보여줄 메시지
    private final HttpStatus status;
    private final String message;

    // enum 생성자: 위에서 MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "...") 이렇게 쓸 때 호출됨
    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getMessage() { return message; }
}
