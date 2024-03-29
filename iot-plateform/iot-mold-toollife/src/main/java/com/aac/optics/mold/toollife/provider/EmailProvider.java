package com.aac.optics.mold.toollife.provider;

import com.aac.optics.common.core.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
@FeignClient(name = "iot-provider-notification", fallback = EmailProvider.EmailProviderFallback.class)
public interface EmailProvider {

    @PostMapping(value = "/message/sendEmail")
    Result sendEmail(@RequestBody Map<String, Object> emailSendForm);


    @Component
    class EmailProviderFallback implements EmailProvider {

        @Override
        public Result sendEmail(Map<String, Object> emailSendForm) {
            return Result.fail();
        }

    }
}
