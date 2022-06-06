package com.aac.optics.wlg.report.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.wlg.report.entity.po.ProductionPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionProjectReportQueryParam extends BaseParam<ProductionPlan> {

    private String mold;

    private String cycle;

    private String projectName;

    private LocalDate dateStart;

    private LocalDate dateEnd;
}
