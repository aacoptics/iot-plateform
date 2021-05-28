package com.aac.optics.provider.organization.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.provider.organization.entity.po.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionQueryParam extends BaseParam<Position> {
    private String name;
}
