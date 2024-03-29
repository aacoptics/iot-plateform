package com.aac.optics.provider.organization.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.provider.organization.entity.po.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceQueryParam extends BaseParam<Resource> {
    private String name;
    private String code;
    private String type;
    private String url;
    private String method;
}
