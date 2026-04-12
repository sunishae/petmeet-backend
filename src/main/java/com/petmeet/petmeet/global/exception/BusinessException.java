package com.petmeet.petmeet.global.exception;

// 이 프로젝트에서 발생하는 모든 비즈니스 예외의 부모 클래스.
// RuntimeException을 상속받기 때문에 throws 선언 없이 어디서든 던질 수 있다.
//
// 사용 패턴:
//   1. 도메인별 예외 클래스(예: MemberNotFoundException)가 이 클래스를 상속
//   2. 서비스에서 도메인 예외를 throw
//   3. GlobalExceptionHandler가 BusinessException을 잡아서 클라이언트에 JSON으로 응답
public class BusinessException extends RuntimeException {

    // 이 예외가 어떤 에러 코드인지 담아두는 필드
    // → GlobalExceptionHandler에서 꺼내서 HTTP 상태코드와 메시지를 결정할 때 사용
    private final ErrorCode errorCode;

    // 자식 예외 클래스에서 super(ErrorCode.XXX)로 호출하는 생성자
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // RuntimeException에 메시지 전달 (로그에 찍힘)
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
