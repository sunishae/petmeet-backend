package com.petmeet.petmeet.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 에러 발생 시 클라이언트에게 돌려주는 응답 형태를 정의한 클래스.
// 모든 에러 응답이 이 구조로 통일됨:
// {
//   "code": "MEMBER_NOT_FOUND",
//   "message": "존재하지 않는 회원입니다."
// }
@Getter
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 자동 생성 (new ErrorResponse(code, message) 가능)
public class ErrorResponse {
    private String code;    // ErrorCode의 이름 (예: "MEMBER_NOT_FOUND") — 프론트엔드가 분기 처리할 때 사용
    private String message; // 사용자에게 보여줄 메시지 (예: "존재하지 않는 회원입니다.")
}
