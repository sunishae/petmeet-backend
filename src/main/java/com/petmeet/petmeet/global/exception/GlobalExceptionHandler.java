package com.petmeet.petmeet.global.exception;

import com.petmeet.petmeet.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import org.springframework.web.servlet.resource.NoResourceFoundException;

// @Slf4j: log.error(), log.info() 등 로그를 찍을 수 있게 해주는 Lombok 애노테이션
@Slf4j
// @RestControllerAdvice: 모든 @RestController에서 발생하는 예외를 이 클래스에서 일괄 처리.
// 쉽게 말해 "예외 중앙 처리반". 컨트롤러마다 try-catch 안 써도 되는 이유가 이것.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler: 특정 예외 타입이 발생했을 때 이 메서드가 호출되도록 등록.
    // BusinessException (우리 프로젝트의 모든 비즈니스 예외)을 처리.
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        ErrorCode code = e.getErrorCode();
        // ErrorCode에 정의된 HTTP 상태코드와 메시지를 그대로 응답으로 내려줌
        return ResponseEntity.status(code.getStatus())
                .body(new ErrorResponse(code.name(), code.getMessage()));
    }

    // @Valid 검증 실패 시 Spring이 자동으로 던지는 예외.
    // 예: @NotBlank인데 빈 값이 들어오면 이 메서드가 호출됨.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        // 여러 필드에 에러가 있을 수 있으므로, 모든 에러 메시지를 쉼표로 합쳐서 반환
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("INVALID_INPUT", message));
    }

    // 위 두 핸들러에서 잡히지 않은 예외는 모두 여기서 처리 (최후 방어선).
    // 예상치 못한 서버 오류를 클라이언트에게 500으로 응답하고, 서버 로그에 기록.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandled exception", e); // 서버 로그에 스택 트레이스 출력
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("INTERNAL_ERROR", "서버 오류가 발생했습니다."));
    }

    // 존재하지 않는 URL로 요청했을 때 Spring이 던지는 예외 (예: GET /api/v1/없는경로).
    // 처리하지 않으면 handleException()이 잡아서 500을 반환하기 때문에 별도로 분리.
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", e.getMessage()));
    }
}
