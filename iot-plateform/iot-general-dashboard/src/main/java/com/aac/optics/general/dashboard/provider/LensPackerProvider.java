package com.aac.optics.general.dashboard.provider;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.general.dashboard.exception.DashboardErrorType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "iot-lenspacker-dashboard", fallback = LensPackerProvider.LensPackerProviderFallback.class)
public interface LensPackerProvider {

    @GetMapping(value = "/lenspackerDashboard/getStatusCount")
    Result getLensPackerStatusCount();

    @Component
    class LensPackerProviderFallback implements LensPackerProvider {

        @Override
        public Result getLensPackerStatusCount() {
            return Result.fail(DashboardErrorType.INNER_ERROR);
        }
    }
}
