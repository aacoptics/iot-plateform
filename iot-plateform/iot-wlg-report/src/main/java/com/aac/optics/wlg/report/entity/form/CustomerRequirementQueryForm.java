package com.aac.optics.wlg.report.entity.form;

import com.aac.optics.common.web.entity.form.BaseQueryForm;
import com.aac.optics.wlg.report.entity.param.CustomerRequirementQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ApiModel
@Data
public class CustomerRequirementQueryForm extends BaseQueryForm<CustomerRequirementQueryParam> {

    @ApiModelProperty(value = "项目")
    private String projectName;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ApiModelProperty(value = "查询需求开始日期")
    private LocalDate requirementDateStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ApiModelProperty(value = "查询需求结束日期")
    private LocalDate requirementDateEnd;
}
