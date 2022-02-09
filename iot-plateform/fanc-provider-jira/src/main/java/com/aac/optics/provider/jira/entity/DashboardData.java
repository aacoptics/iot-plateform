package com.aac.optics.provider.jira.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Shao Xiang
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("JIRA_00_DASHBOARD")
public class DashboardData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long ID;

    private Integer DASHBOARD_ID;

    private String DASHBOARD_NAME;

    private String DASHBOARD_LINK;

    @TableField("CREATE_TIME")
    private LocalDateTime CREATE_TIME;

    @TableField("CREATE_TIME")
    private LocalDateTime UPDATE_TIME;
}
