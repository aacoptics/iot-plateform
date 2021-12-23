package com.aac.optics.dingtalk.filelog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("DINGTALK_FILE_LOG")
public class DingTalkFileLog implements Serializable {

    private static final long serialVersionUID = 3152613753153716698L;

    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    @TableField(value = "actionCode")
    private Long action;

    @TableField(value = "actionView")
    private String actionView;

    @TableField(value = "bizId")
    private String bizId;

    @TableField(value = "fileCreateTime")
    private LocalDateTime fileCreateTime;

    @TableField(value = "fileModifiedTime")
    private LocalDateTime fileModifiedTime;

    @TableField(value = "ipAddress")
    private String ipAddress;

    @TableField(value = "operateModule")
    private Long operateModule;

    @TableField(value = "operateModuleView")
    private String operateModuleView;

    @TableField(value = "operatorName")
    private String operatorName;

    @TableField(value = "orgName")
    private String orgName;

    @TableField(value = "platform")
    private Long platform;

    @TableField(value = "platformView")
    private String platformView;

    @TableField(value = "realName")
    private String realName;

    @TableField(value = "receiverName")
    private String receiverName;

    @TableField(value = "receiverType")
    private Long receiverType;

    @TableField(value = "receiverTypeView")
    private String receiverTypeView;

    @TableField(value = "fileResource")
    private String resource;

    @TableField(value = "resourceExtension")
    private String resourceExtension;

    @TableField(value = "resourceSize")
    private Long resourceSize;

    @TableField(value = "status")
    private Long status;

    @TableField(value = "targetSpaceId")
    private Long targetSpaceId;

    @TableField(value = "userid")
    private String userid;

    @TableField(value = "jobNumber")
    private String jobNumber;

    @TableField(value = "fdId")
    private String fdId;

    @TableField(value = "fdName")
    private String fdName;

    @TableField(value = "createDate")
    private LocalDateTime createDate;
}