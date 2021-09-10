package com.aac.optics.mold.toollife.controller;

import com.aac.optics.common.core.exception.SystemErrorType;
import com.aac.optics.common.core.vo.Result;
import com.aac.optics.mold.toollife.model.ToolInfo;
import com.aac.optics.mold.toollife.service.ToolInfoService;
import com.aac.optics.mold.toollife.util.ExcelUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/toolLife")
@Api("moldToolLife")
@Slf4j
public class MoldToolLifeController {
    @Autowired
    ToolInfoService toolInfoService;


    @ApiOperation(value = "刀具寿命Excel上传", notes = "刀具寿命Excel上传")
    @ApiImplicitParam(name = "file", value = "Excel文件", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestPart("file") MultipartFile file) {
        List<ToolInfo> toolInfos = null;
        try {
            toolInfos = toolInfoService.phaseExcelData(file.getInputStream());
            return Result.success(toolInfos);
        } catch (Exception e) {
            return Result.fail(SystemErrorType.SYSTEM_ERROR);
        }
    }

    @ApiOperation(value = "根据MonitorNo查询刀具寿命Excel", notes = "根据MonitorNo查询刀具寿命Excel")
    @ApiImplicitParam(name = "monitorNo", value = "监控号", required = true, dataType = "String")
    @GetMapping("/getByMonitorNo")
    public Result getByMonitorNo(@RequestParam String monitorNo) {
        return Result.success(toolInfoService.getToolInfo(monitorNo));
    }

    @ApiOperation(value = "更新机台号，刀位，刀具编号", notes = "更新机台号，刀位，刀具编号")
    @ApiImplicitParam(name = "toolInfo", value = "信息", required = true, dataType = "ToolInfo")
    @PostMapping("/updateToolInfo")
    public Result getByMonitorNo(@RequestBody List<ToolInfo> toolInfos) {
        boolean res = toolInfoService.updateToolLifeInfo(toolInfos);
        if(res){
            return Result.success();
        }else{
            return Result.fail();
        }
    }
}