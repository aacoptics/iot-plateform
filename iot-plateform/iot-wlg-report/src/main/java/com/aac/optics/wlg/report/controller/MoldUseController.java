package com.aac.optics.wlg.report.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.wlg.report.entity.form.MoldUseQueryForm;
import com.aac.optics.wlg.report.entity.param.MoldUseQueryParam;
import com.aac.optics.wlg.report.service.MoldUseService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/moldUse")
@Api("moldUse")
@Slf4j
public class MoldUseController {

    @Autowired
    MoldUseService moldUseService;


    @ApiOperation(value = "模具使用情况Excel上传", notes = "模具使用情况Excel上传")
    @ApiImplicitParam(name = "file", value = "Excel文件", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestPart("file") MultipartFile file) throws Exception {

        moldUseService.importMoldUseExcel(file.getInputStream());
        return Result.success();
    }


    @ApiOperation(value = "获取模具使用情况", notes = "获取模具使用情况")
    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(moldUseService.getAll());
    }


    @ApiOperation(value = "搜索模具使用情况", notes = "根据条件搜索模具使用情况信息")
    @ApiImplicitParam(name = "moldUseQueryForm", value = "模具使用情况查询参数", required = true, dataType = "MoldUseQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/conditions")
    public Result conditions(@Valid @RequestBody MoldUseQueryForm moldUseQueryForm) {
        log.debug("query with name:{}", moldUseQueryForm);
        return Result.success(moldUseService.query(moldUseQueryForm.getPage(), moldUseQueryForm.toParam(MoldUseQueryParam.class)));
    }

    @ApiOperation(value = "搜索模具使用情况列转列表头", notes = "搜索模具使用情况列转列表头")
    @ApiImplicitParam(name = "moldUseQueryForm", value = "模具使用情况查询参数", required = true, dataType = "MoldUseQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/queryMoldUseTitleByMonth")
    public Result queryMoldUseTitleByMonth(@Valid @RequestBody MoldUseQueryForm moldUseQueryForm) {
        log.debug("query with name:{}", moldUseQueryForm);
        return Result.success(moldUseService.queryMoldUseTitleByMonth(moldUseQueryForm.toParam(MoldUseQueryParam.class)));
    }


    @ApiOperation(value = "搜索模具使用情况", notes = "根据条件搜索模具使用情况信息")
    @ApiImplicitParam(name = "moldUseQueryForm", value = "模具使用情况查询参数", required = true, dataType = "MoldUseQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/queryMoldUseByMonth")
    public Result queryMoldUseByMonth(@Valid @RequestBody MoldUseQueryForm moldUseQueryForm) {
        log.debug("query with name:{}", moldUseQueryForm);
        return Result.success(moldUseService.queryMoldUseByMonth(moldUseQueryForm.toParam(MoldUseQueryParam.class)));
    }
}