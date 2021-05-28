package com.aac.optics.provider.organization.exception;

import com.aac.optics.common.core.exception.BaseException;
import com.aac.optics.common.core.exception.ErrorType;

public class AuthenticationException extends BaseException {
    public AuthenticationException(ErrorType errorType) {
        super(errorType, errorType.getMsg());
    }
}
