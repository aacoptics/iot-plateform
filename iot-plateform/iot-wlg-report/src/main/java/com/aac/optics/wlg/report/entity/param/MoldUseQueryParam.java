package com.aac.optics.wlg.report.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.wlg.report.entity.po.MoldUse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoldUseQueryParam extends BaseParam<MoldUse> {
    private String projectName;

    private LocalDate moldDateStart;

    private LocalDate moldDateEnd;
}
