package com.aac.optics.provider.organization.entity.po;

import com.aac.optics.common.web.entity.po.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("role_menu_relation")
public class RoleMenu extends BasePo {
    private Long roleId;
    private Long menuId;
}
