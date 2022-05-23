package com.aac.optics.wlg.dashboard.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.wlg.dashboard.entity.MoldingDataParam;
import com.aac.optics.wlg.dashboard.service.MoldingMachineParamDataService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/customerRequirement")
@Api("customerRequirement")
@Slf4j
public class MoldingParamController {

    @Resource()
    MoldingMachineParamDataService moldingMachineParamDataService;


    @ApiOperation(value = "获取所有机台号", notes = "获取所有机台号")
    @GetMapping("/getMoldingMachineName")
    public Result getMoldingMachineName() {
        return Result.success(moldingMachineParamDataService.getMachineName());
    }


    @ApiOperation(value = "获取这段时间的waferId", notes = "获取这段时间的waferId")
    @GetMapping(value = "/getWaferIds")
    public Result getWaferIds(@RequestParam String machineName,
                              @RequestParam LocalDateTime startTime,
                              @RequestParam LocalDateTime endTime) {
        return Result.success(moldingMachineParamDataService.getWaferIds(machineName, startTime, endTime));
    }

    @ApiOperation(value = "获取模造机参数", notes = "获取模造机参数")
    @PostMapping(value = "/getMoldParam")
    public Result getParamData(@RequestBody MoldingDataParam moldingDataParam) {
        return Result.success(moldingMachineParamDataService.getMoldingParamData(moldingDataParam.getMachineName(), moldingDataParam.getParamName(), moldingDataParam.getWaferIds()));
    }

    @ApiOperation(value = "获取模造机参数名", notes = "获取模造机参数名")
    @PostMapping(value = "/getMoldParamName")
    public Result getMoldParamName(@RequestBody MoldingDataParam moldingDataParam) {
        return Result.success(moldingMachineParamDataService.getMoldingParamName(moldingDataParam.getMachineName(), moldingDataParam.getWaferIds()));
    }
}