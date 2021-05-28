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
@TableName("user_role_relation")
public class UserRole extends BasePo {
    private Long userId;
    private Long roleId;
}
