package com.aac.optics.provider.organization.entity.form;

import com.aac.optics.common.web.entity.form.BaseForm;
import com.aac.optics.provider.organization.entity.po.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class MenuForm extends BaseForm<Menu> {

    @NotBlank(message = "菜单父id不能为空")
    @ApiModelProperty(value = "菜单父id")
    private Long parentId;

    @NotBlank(message = "菜单英文名称不能为空")
    @ApiModelProperty(value = "菜单英文名称")
    private String name;

    @NotBlank(message = "菜单中文名称不能为空")
    @ApiModelProperty(value = "菜单中文名称")
    private String title;

    @NotBlank(message = "菜单路径不能为空")
    @ApiModelProperty(value = "菜单路径")
    private String path;

    @NotBlank(message = "菜单VUE路径不能为空")
    @ApiModelProperty(value = "菜单VUE路径")
    private String component;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单序号")
    private Integer orderNum;

    @ApiModelProperty(value = "菜单描述")
    private String description;



}
