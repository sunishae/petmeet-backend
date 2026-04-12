package com.petmeet.petmeet.breed.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

// 품종을 찾지 못했을 때 던지는 예외.
// BusinessException을 상속받아서 GlobalExceptionHandler가 자동으로 처리해준다.
//
// 이렇게 도메인별로 예외 클래스를 분리하는 이유:
//   1. 서비스 코드에서 의도가 명확해진다. (throw new BreedNotFoundException() vs throw new BusinessException(ErrorCode.BREED_NOT_FOUND))
//   2. 나중에 특정 예외만 별도로 처리해야 할 경우 확장이 쉽다.
public class BreedNotFoundException extends BusinessException {

    public BreedNotFoundException() {
        // 부모 클래스(BusinessException)의 생성자에 에러 코드를 전달
        super(ErrorCode.BREED_NOT_FOUND);
    }
}
