package com.aac.optics.provider.organization.entity.po;

import com.aac.optics.common.web.entity.po.BasePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("iot_sys_menu")
public class Menu extends BasePo {
    private Long parentId;
    private String name;
    private String path;
    private String component;
    private String title;
    private String icon;
    private Integer orderNum;
    private String description;
    private String webUrl;
    @TableField(exist = false)
    private Set<Menu> children;
    @TableField(exist = false)
    private String roles;
}