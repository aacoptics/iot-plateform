package com.aac.optics.provider.organization.entity.param;

import com.aac.optics.common.web.entity.param.BaseParam;
import com.aac.optics.provider.organization.entity.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryParam extends BaseParam<User> {
    private String name;
    private String mobile;
    private String username;
}
