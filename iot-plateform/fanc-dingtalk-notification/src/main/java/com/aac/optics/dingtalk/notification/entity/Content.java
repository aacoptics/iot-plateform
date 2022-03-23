package com.aac.optics.dingtalk.notification.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("tab_01_content")
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String tabDate;

    private String tabType;

    private String title;

    private String content;

    private String flag;

    private String createdBy;

    private LocalDateTime createTime;

    private String modifyBy;

    private LocalDateTime modifyTime;
}