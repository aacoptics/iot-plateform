package com.aac.optics.wlg.report.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.wlg.report.entity.form.ProductionDayReportQueryForm;
import com.aac.optics.wlg.report.entity.form.ProductionMonthReportQueryForm;
import com.aac.optics.wlg.report.entity.param.ProductionDayReportQueryParam;
import com.aac.optics.wlg.report.entity.param.ProductionMonthReportQueryParam;
import com.aac.optics.wlg.report.service.ProductionReportService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/productionReport")
@Api("productionReport")
@Slf4j
public class ProductionReportController {

    @Autowired
    ProductionReportService productionReportService;


    @ApiOperation(value = "搜索生产月度汇总列转列表头", notes = "搜索生产月度汇总列转列表头")
    @ApiImplicitParam(name = "productionMonthReportQueryForm", value = "生产月度汇总查询参数", required = true, dataType = "ProductionMonthReportQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/queryProductionMonthReportTitleByCondition")
    public Result queryProductionMonthReportTitleByCondition(@Valid @RequestBody ProductionMonthReportQueryForm productionMonthReportQueryForm) {
        log.debug("queryProductionMonthReportTitleByCondition with name:{}", productionMonthReportQueryForm);
        return Result.success(productionReportService.queryProductionMonthReportTitleByCondition(productionMonthReportQueryForm.toParam(ProductionMonthReportQueryParam.class)));
    }


    @ApiOperation(value = "搜索生产月度汇总", notes = "根据条件搜索生产月度汇总信息")
    @ApiImplicitParam(name = "productionMonthReportQueryForm", value = "生产月度汇总查询参数", required = true, dataType = "ProductionMonthReportQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/queryProductionMonthReportByCondition")
    public Result queryProductionMonthReportByCondition(@Valid @RequestBody ProductionMonthReportQueryForm productionMonthReportQueryForm) {
        log.debug("queryProductionMonthReportByCondition with name:{}", productionMonthReportQueryForm);
        return Result.success(productionReportService.queryProductionMonthReportByCondition(productionMonthReportQueryForm.toParam(ProductionMonthReportQueryParam.class)));
    }

    @ApiOperation(value = "搜索生产日报表", notes = "根据条件搜索搜索生产日报表")
    @ApiImplicitParam(name = "productionDayReportQueryForm", value = "搜索生产日报表查询参数", required = true, dataType = "ProductionDayReportQueryForm")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/queryProductionDayReportByCondition")
    public Result queryProductionDayReportByCondition(@Valid @RequestBody ProductionDayReportQueryForm productionDayReportQueryForm) {
        log.debug("queryProductionDayReportByCondition with name:{}", productionDayReportQueryForm);
        return Result.success(productionReportService.queryProductionDayReportByCondition(productionDayReportQueryForm.toParam(ProductionDayReportQueryParam.class)));
    }
}