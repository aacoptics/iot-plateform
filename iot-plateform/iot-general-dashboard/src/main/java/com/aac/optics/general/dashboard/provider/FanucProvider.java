package com.aac.optics.general.dashboard.provider;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.general.dashboard.exception.DashboardErrorType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "iot-fanuc-dashboard", fallback = FanucProvider.FanucProviderFallback.class)
public interface FanucProvider {

    @GetMapping(value = "/fanucDashboard/getStatusCount")
    Result getStatusCount();

    @Component
    class FanucProviderFallback implements FanucProvider {

        @Override
        public Result getStatusCount() {
            return Result.fail(DashboardErrorType.INNER_ERROR);
        }
    }
}
