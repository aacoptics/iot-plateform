package com.aac.optics.provider.organization.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.aac.optics.common.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("roles")
public class Role extends BasePo {
    private String code;
    private String name;
    private String description;

    @TableField(exist = false)
    private Set<Long> resourceIds;

    @TableField(exist = false)
    private Set<Long> menuIds;
}
