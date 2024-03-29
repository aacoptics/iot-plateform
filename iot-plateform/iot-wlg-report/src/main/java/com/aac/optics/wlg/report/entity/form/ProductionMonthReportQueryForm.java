package com.aac.optics.wlg.report.entity.form;

import com.aac.optics.common.web.entity.form.BaseQueryForm;
import com.aac.optics.wlg.report.entity.param.ProductionMonthReportQueryParam;
import com.aac.optics.wlg.report.entity.param.ProductionPlanQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ApiModel
@Data
public class ProductionMonthReportQueryForm extends BaseQueryForm<ProductionMonthReportQueryParam> {

    @ApiModelProperty(value = "项目")
    private String projectName;

    @ApiModelProperty(value = "模具")
    private String mold;

    @ApiModelProperty(value = "周期")
    private String cycle;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ApiModelProperty(value = "查询业务发生开始日期")
    private LocalDate dateStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ApiModelProperty(value = "查询业务发生结束日期")
    private LocalDate dateEnd;
}
