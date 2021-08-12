package com.aac.optics.fanuc.dashboard.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.fanuc.dashboard.service.FanucAlarmDataService;
import com.aac.optics.fanuc.dashboard.service.FanucCondDataService;
import com.aac.optics.fanuc.dashboard.service.FanucDashboardService;
import com.aac.optics.fanuc.dashboard.service.FanucMonitDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fanucDigital")
@Api("fanuc")
@Slf4j
public class FanucDigitalController {
    @Autowired
    FanucDashboardService fanucDashboardService;

    @ApiOperation(value = "实时状态数据", notes = "查询实时状态数据")
    @GetMapping("/getDigitalData")
    public Result getDigitalData() {
        return Result.success(fanucDashboardService.getDigitalData());
    }


}
