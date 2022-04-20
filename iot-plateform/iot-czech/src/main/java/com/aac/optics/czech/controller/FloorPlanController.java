package com.aac.optics.czech.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.czech.service.FloorPlanMachineInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/floorPlan")
@Api("czechFloorPlan")
@Slf4j
public class FloorPlanController {
    @Autowired
    FloorPlanMachineInfoService floorPlanMachineInfoService;

    @ApiOperation(value = "根据楼层查询机台信息", notes = "根据楼层查询机台信息")
    @GetMapping("/getMachineInfoByFloor")
    public Result getMachineInfoByFloor(@RequestParam int startNumber, @RequestParam int endNumber) {
        return Result.success(floorPlanMachineInfoService.getMachineInfoByFloor(startNumber, endNumber));
    }

    @ApiOperation(value = "根据楼层号查询机台信息", notes = "根据楼层号查询机台信息")
    @GetMapping("/getMachineInfoByFloorNumber")
    public Result getMachineInfoByFloorNumber(@RequestParam int floorNumber) {
        return Result.success(floorPlanMachineInfoService.getMachineInfoByFloorNumber(floorNumber));
    }

    @ApiOperation(value = "根据机台号查询机台信息", notes = "根据机台号查询机台信息")
    @GetMapping("/getMachineInfoByMachineNumber")
    public Result getMachineInfoByMachineNumber(@RequestParam int machineNumber) {
        return Result.success(floorPlanMachineInfoService.getMachineInfoByMachineNumber(machineNumber));
    }

    @ApiOperation(value = "获取Spindle T温度数据", notes = "获取Spindle T温度数据")
    @GetMapping("/getSpindleTemperature")
    public Result getSpindleTemperature(@RequestParam String startTime, @RequestParam String endTime, @RequestParam int machineNumber) {
        return Result.success(floorPlanMachineInfoService.getSpindleTemperature(startTime, endTime, machineNumber));
    }

    @ApiOperation(value = "获取Air T温度数据", notes = "获取Air T温度数据")
    @GetMapping("/getAirTemperature")
    public Result getAirTemperature(@RequestParam String startTime, @RequestParam String endTime, @RequestParam int machineNumber) {
        return Result.success(floorPlanMachineInfoService.getAirTemperature(startTime, endTime, machineNumber));
    }

    @ApiOperation(value = "获取Bearing T温度数据", notes = "获取Bearing T温度数据")
    @GetMapping("/getBearingTemperature")
    public Result getBearingTemperature(@RequestParam String startTime, @RequestParam String endTime, @RequestParam int machineNumber) {
        return Result.success(floorPlanMachineInfoService.getBearingTemperature(startTime, endTime, machineNumber));
    }

    @ApiOperation(value = "获取Motor T温度数据", notes = "获取Motor T温度数据")
    @GetMapping("/getMotorTemperature")
    public Result getMotorTemperature(@RequestParam String startTime, @RequestParam String endTime, @RequestParam int machineNumber) {
        return Result.success(floorPlanMachineInfoService.getMotorTemperature(startTime, endTime, machineNumber));
    }

    @ApiOperation(value = "根据机台号获取状态信息", notes = "根据机台号获取状态信息")
    @GetMapping("/getStatusInfoByMachineNumber")
    public Result getStatusInfoByMachineNumber(@RequestParam int machineNumber) {
        return Result.success(floorPlanMachineInfoService.getStatusInfoByMachineNumber(machineNumber));
    }
}
