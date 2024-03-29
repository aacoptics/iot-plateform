package com.aac.optics.auth.server.exception;

import com.aac.optics.common.core.vo.Result;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
class CustomOauthException extends OAuth2Exception {

    @Getter
    private final Result result;

    CustomOauthException(OAuth2Exception oAuth2Exception) {
        super(oAuth2Exception.getSummary(), oAuth2Exception);
        this.result = Result.fail(AuthErrorType.valueOf(oAuth2Exception.getOAuth2ErrorCode().toUpperCase()), oAuth2Exception);
    }
}