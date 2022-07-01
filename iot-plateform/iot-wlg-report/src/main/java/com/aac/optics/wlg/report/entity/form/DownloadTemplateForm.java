package com.aac.optics.wlg.report.entity.form;

import com.aac.optics.common.web.entity.form.BaseForm;
import com.aac.optics.common.web.entity.po.BasePo;
import com.aac.optics.wlg.report.entity.po.ProductionSummary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel
@Data
public class DownloadTemplateForm extends BaseForm<BasePo> {

    @ApiModelProperty(value = "模板类型")
    private String type;


}
