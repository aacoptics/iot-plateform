package com.aac.optics.mold.toollife.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("INV_TOOL_SCRAP_DETAIL")
public class ScrapedTool implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codeNo;

    private String matCode;

    private String matName;

    private String lifeSalvage;

    private String createDate;




}
