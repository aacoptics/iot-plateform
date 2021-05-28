package com.aac.optics.provider.organization.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.provider.organization.entity.po.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupQueryParam extends BaseParam<Group> {
    private String name;
}
