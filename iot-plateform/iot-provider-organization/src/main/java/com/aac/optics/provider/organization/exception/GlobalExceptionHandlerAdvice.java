package com.aac.optics.provider.organization.exception;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.common.web.exception.DefaultGlobalExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvice extends DefaultGlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public Result userNotFound(UserNotFoundException ex) {
        log.error(ex.getMessage());
        return Result.fail(ex.getErrorType());
    }
}