package com.aac.optics.common.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {
    private static final String X_CLIENT_TOKEN = "x-client-token";

    @Override
    public void apply(RequestTemplate template) {
        template.header(X_CLIENT_TOKEN, "test");
    }
}