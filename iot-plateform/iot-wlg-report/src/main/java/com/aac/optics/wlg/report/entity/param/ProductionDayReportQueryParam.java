package com.aac.optics.wlg.report.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.wlg.report.entity.po.ProductionPlan;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDayReportQueryParam extends BaseParam<ProductionPlan> {

    private String projectName;

    private String mold;

    private String cycle;

    private LocalDate dateStart;

    private LocalDate dateEnd;
}
