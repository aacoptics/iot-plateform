package com.aac.optics.wlg.report.controller;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.wlg.report.entity.form.ProductionDayReportQueryForm;
import com.aac.optics.wlg.report.entity.form.ProductionMonthReportQueryForm;
import com.aac.optics.wlg.report.entity.form.ProductionProjectReportQueryForm;
import com.aac.optics.wlg.report.entity.param.ProductionDayReportQueryParam;
import com.aac.optics.wlg.report.entity.param.ProductionMonthReportQueryParam;
import com.aac.optics.wlg.report.entity.param.ProductionProjectReportQueryParam;
import com.aac.optics.wlg.report.service.DingTalkNotificationService;
import com.aac.optics.wlg.report.service.ProductionReportService;
import com.aac.optics.wlg.report.util.ExcelUtil;
import com.taobao.api.ApiException;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dingTalkMessage")
@Api("dingTalkMessage")
@Slf4j
public class DingTalkMessageController {

    @Autowired
    DingTalkNotificationService dingTalkNotificationService;


    @ApiOperation(value = "推送WLG数据到钉钉测试群（表格模式）", notes = "推送WLG数据到钉钉测试群（表格模式）")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @PostMapping(value = "/sendProductionDayDataImageNotification")
    public Result sendProductionDayDataImageNotification() {
        try {
            dingTalkNotificationService.sendProductionDayDataImageNotification("TABLE_TEST");
        } catch (Exception e) {
            log.error("推送失败", e);
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }


}