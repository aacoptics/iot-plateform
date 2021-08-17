package com.aac.optics.general.dashboard.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.general.dashboard.provider.LensPackerProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/generalDashboard")
@Api("general")
@Slf4j
public class GeneralDashboardController {

    @Autowired
    LensPackerProvider lensPackerProvider;

    @ApiOperation(value = "查询机台报警详细数据", notes = "查询机台报警详细数据")
    @GetMapping("/getLensPackerStatusCount")
    public Result getMachineAlarmDetail() {
        return Result.success(lensPackerProvider.getLensPackerStatusCount());
    }


}