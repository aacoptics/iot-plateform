package com.aac.optics.provider.organization.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;

import com.aac.optics.provider.organization.entity.po.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryParam extends BaseParam<Menu> {
    private String title;
}
