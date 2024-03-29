package com.aac.optics.wlg.report.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.wlg.report.entity.po.EstimateFpy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateFpyQueryParam extends BaseParam<EstimateFpy> {
    private String projectName;

    private String mold;

    private String cycle;

    private LocalDate fpyDateStart;

    private LocalDate fpyDateEnd;
}
