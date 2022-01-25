package com.aac.optics.provider.jira.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("JIRA_00_ISSUE")
public class IssueData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long ID;

    private String ISSUE_KEY;

    private String ISSUE_NAME;

    private String EKP_ISSUE_NO;

    private String ISSUE_TYPE;

    private String DOMAIN;

    private String STATUS;

    @TableField("START_TIME")
    private LocalDateTime START_TIME;

    @TableField("END_TIME")
    private LocalDateTime END_TIME;

    private String BUSINESS_OWNER;

    private BigDecimal BUSINESS_COST;

    private String DEVELOP_OWNER;

    private BigDecimal DEVELOP_COST;

    @TableField("CREATE_TIME")
    private LocalDateTime CREATE_TIME;

    @TableField("UPDATE_TIME")
    private LocalDateTime UPDATE_TIME;
}
