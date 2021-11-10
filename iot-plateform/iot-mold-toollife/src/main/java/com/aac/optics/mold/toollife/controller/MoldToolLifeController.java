package com.aac.optics.mold.toollife.controller;

import com.aac.optics.common.core.exception.SystemErrorType;
import com.aac.optics.common.core.vo.Result;
import com.aac.optics.mold.toollife.entity.AbnormalTool;
import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.aac.optics.mold.toollife.entity.ToolInfo;
import com.aac.optics.mold.toollife.entity.UpdateSheetForm;
import com.aac.optics.mold.toollife.exception.ToolLofeErrorType;
import com.aac.optics.mold.toollife.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/toolLife")
@Api("moldToolLife")
@Slf4j
public class MoldToolLifeController {
    @Autowired
    ToolInfoService toolInfoService;

    @Autowired
    EquipInfoService equipInfoService;

    @Autowired
    MatInfoService matInfoService;

    @Autowired
    ProgramDetailService programDetailService;

    @Autowired
    MachineAreaInfoService machineAreaInfoService;

    @Autowired
    AbnormalToolService abnormalToolService;

    @Autowired
    AbnormalTypeService abnormalTypeService;


    @ApiOperation(value = "刀具寿命Excel上传", notes = "刀具寿命Excel上传")
    @ApiImplicitParam(name = "file", value = "Excel文件", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestPart("file") MultipartFile file) {
        String monitorNo = "";
        try {
            monitorNo = toolInfoService.phaseExcelData(file.getInputStream());
            if (StringUtils.isBlank(monitorNo)) {
                Result.fail(ToolLofeErrorType.MONITOR_IS_BLANK);
            }
            return Result.success(monitorNo);
        } catch (Exception e) {
            return Result.fail(SystemErrorType.SYSTEM_ERROR);
        }
    }

    @ApiOperation(value = "查询异常种类", notes = "查询异常种类")
    @GetMapping("/getAbnormalType")
    public Result getAbnormalType() {
        return Result.success(abnormalTypeService.getAbnormalType());
    }

    @ApiOperation(value = "根据MonitorNo查询刀具寿命Excel", notes = "根据MonitorNo查询刀具寿命Excel")
    @ApiImplicitParam(name = "monitorNo", value = "监控号", required = true, dataType = "String")
    @GetMapping("/getByMonitorNo")
    public Result getByMonitorNo(@RequestParam String monitorNo) {
        return Result.success(toolInfoService.getToolInfo(monitorNo));
    }

    @ApiOperation(value = "根据MonitorNo查询维护状态", notes = "根据MonitorNo查询维护状态")
    @ApiImplicitParam(name = "monitorNo", value = "监控号", required = true, dataType = "ArrayList")
    @PostMapping("/getToolMaintainStatus")
    public Result getToolMaintainStatus(@RequestBody List<String> monitorNos) {
        return Result.success(toolInfoService.getToolMaintainStatus(monitorNos));
    }

    @ApiOperation(value = "查询前一天程序运行总时间", notes = "查询前一天程序运行总时间")
    @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, dataType = "String")
    @GetMapping("/getLastDayOee")
    public Result getLastDayOee(@RequestParam String startTime) {
        return Result.success(programDetailService.getLastDayOee(startTime));
    }

    @ApiOperation(value = "查询机台区域信息", notes = "查询机台区域信息")
    @GetMapping("/getAreaInfo")
    public Result getAreaInfo() {
        return Result.success(machineAreaInfoService.getAreaConfig());
    }

    @ApiOperation(value = "查询前一天报废退库数量", notes = "查询前一天报废退库数量")
    @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, dataType = "String")
    @GetMapping("/getLastDayScrapCount")
    public Result getLastDayScrapCount(@RequestParam String startTime) {
        Integer scrapCount = matInfoService.getScrapCount(startTime);
        Integer outCount = matInfoService.getOutCount(startTime);
        Map<String, Integer> res = new HashMap<>();
        res.put("scrap", scrapCount);
        res.put("out", outCount);
        return Result.success(res);
    }

    @ApiOperation(value = "查询刀具历史明细", notes = "查询刀具历史明细")
    @ApiImplicitParam(name = "toolCode", value = "刀具号", required = true, dataType = "String")
    @GetMapping("/getToolHisList")
    public Result getToolHisList(@RequestParam String toolCode) {
        return Result.success(programDetailService.getToolHisList(toolCode));
    }


    @ApiOperation(value = "获取异常条目", notes = "获取异常条目")
    @GetMapping("/getAbnormalList")
    public Result getAbnormalList() {
        return Result.success(abnormalToolService.getAbnormalTools());
    }

    @ApiOperation(value = "更新机台号，刀位，刀具编号", notes = "更新机台号，刀位，刀具编号")
    @ApiImplicitParam(name = "toolInfo", value = "信息", required = true, dataType = "ToolInfo")
    @PostMapping("/updateToolInfo")
    public Result updateToolInfo(@RequestBody UpdateSheetForm updateSheetForm) {
        boolean res;
        if (StringUtils.isBlank(updateSheetForm.getMachineNo()))
            res = toolInfoService.updateToolLifeInfo(updateSheetForm.getToolInfos());
        else
            res = toolInfoService.addMachineToolLifeInfo(updateSheetForm);
        if (res) {
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "添加刀具异常原因", notes = "添加刀具异常原因")
    @ApiImplicitParam(name = "abnormalTool", value = "异常刀具", required = true, dataType = "AbnormalTool")
    @PostMapping("/addAbnormalReason")
    public Result addAbnormalReason(@RequestBody AbnormalTool abnormalTool) {
        abnormalToolService.updateAbnormalReason(abnormalTool);
        return Result.success();
    }

    @ApiOperation(value = "确认刀具异常原因", notes = "确认刀具异常原因")
    @ApiImplicitParam(name = "abnormalTool", value = "异常刀具", required = true, dataType = "AbnormalTool")
    @PostMapping("/confirmAbnormalReason")
    public Result confirmAbnormalReason(@RequestBody AbnormalTool abnormalTool) {
        abnormalToolService.updateConfirmInfo(abnormalTool);
        return Result.success();
    }

    @ApiOperation(value = "获取机台号列表", notes = "获取机台号列表")
    @GetMapping("/allMachine")
    public Result getAllMachine() {
        return Result.success(equipInfoService.getMachineNames());
    }

    @ApiOperation(value = "获取前一天异常数量", notes = "获取前一天异常数量")
    @GetMapping("/getLastDayAbnormalCount")
    public Result getLastDayAbnormalCount(@RequestParam String startTime, @RequestParam String endTime) {
        return Result.success(abnormalToolService.getAbnormalCount(startTime, endTime));
    }

    @ApiOperation(value = "获取刀具刀柄信息", notes = "获取刀具刀柄信息")
    @GetMapping("/allMatInfo")
    public Result getAllMatInfo() {
        return Result.success(matInfoService.getMatInfo());
    }
}