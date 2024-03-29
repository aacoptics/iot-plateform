package com.aac.optics.wlg.report.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.wlg.report.entity.po.TargetDelivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetDeliveryQueryParam extends BaseParam<TargetDelivery> {
    private String projectName;

    private LocalDate deliveryDateStart;

    private LocalDate deliveryDateEnd;
}
