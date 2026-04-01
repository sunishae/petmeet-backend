package com.petmeet.petmeet.breed.exception;

import com.petmeet.petmeet.global.exception.BusinessException;
import com.petmeet.petmeet.global.exception.ErrorCode;

public class BreedNotFoundException extends BusinessException {

    public BreedNotFoundException() {
        super(ErrorCode.BREED_NOT_FOUND);
    }
}
