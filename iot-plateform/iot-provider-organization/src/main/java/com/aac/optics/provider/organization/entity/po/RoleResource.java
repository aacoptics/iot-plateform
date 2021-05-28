package com.aac.optics.provider.organization.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.aac.optics.common.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("role_resource_relation")
public class RoleResource extends BasePo {
    private Long roleId;
    private Long resourceId;
}
