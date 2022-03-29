package com.aac.optics.wlg.report.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.wlg.report.entity.form.ProductionPlanQueryForm;
import com.aac.optics.wlg.report.entity.param.ProductionPlanQueryParam;
import com.aac.optics.wlg.report.service.ProductionPlanService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/productionPlan")
@Api("productionPlan")
@Slf4j
public class ProductionPlanController {

    @Autowired
    ProductionPlanService productionPlanService;


    @ApiOperation(value = "生产计划Excel上传", notes = "生产计划Excel上传")
    @ApiImplicitParam(name = "file", value = "Excel文件", required = true, dataType = "MultipartFile")
    @PostMapping("/uploadExcel")
    public Result uploadExcel(@RequestPart("file") MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();
        productionPlanService.importProductionPlanExcel(fileName, file.getInputStream());
        return Result.success();
    }


    @ApiOperation(value = "获取生产计划", notes = "获取生产计划")
    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(productionPlanService.getAll());
    }


    @ApiOperation(value = "搜索生产计划", notes = "根据条件搜索生产计划信息")
    @ApiImplicitParam(name = "productionPlanQueryForm", value = "生产计划查询参数", required = true, dataType = "ProductionPlanQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/conditions")
    public Result conditions(@Valid @RequestBody ProductionPlanQueryForm productionPlanQueryForm) {
        log.debug("query with name:{}", productionPlanQueryForm);
        return Result.success(productionPlanService.query(productionPlanQueryForm.getPage(), productionPlanQueryForm.toParam(ProductionPlanQueryParam.class)));
    }

    @ApiOperation(value = "搜索生产计划列转列表头", notes = "搜索生产计划列转列表头")
    @ApiImplicitParam(name = "productionPlanQueryForm", value = "生产计划查询参数", required = true, dataType = "ProductionPlanQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/queryProductionPlanTitleByMonth")
    public Result queryProductionPlanTitleByMonth(@Valid @RequestBody ProductionPlanQueryForm productionPlanQueryForm) {
        log.debug("query with name:{}", productionPlanQueryForm);
        return Result.success(productionPlanService.queryProductionPlanTitleByMonth(productionPlanQueryForm.toParam(ProductionPlanQueryParam.class)));
    }


    @ApiOperation(value = "搜索生产计划", notes = "根据条件搜索生产计划信息")
    @ApiImplicitParam(name = "productionPlanQueryForm", value = "生产计划查询参数", required = true, dataType = "ProductionPlanQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/queryProductionPlanByMonth")
    public Result queryProductionPlanByMonth(@Valid @RequestBody ProductionPlanQueryForm productionPlanQueryForm) {
        log.debug("query with name:{}", productionPlanQueryForm);
        return Result.success(productionPlanService.queryProductionPlanByMonth(productionPlanQueryForm.toParam(ProductionPlanQueryParam.class)));
    }
}