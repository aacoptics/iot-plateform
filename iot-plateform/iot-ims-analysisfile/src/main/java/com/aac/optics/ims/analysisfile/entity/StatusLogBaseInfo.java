package com.aac.optics.ims.analysisfile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("z_ims_base_info")
public class StatusLogBaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String VersionNumber;

    private String MachineNameShort;

    private String FixtureStatus;

    private String ProductRejectCode;

    private String ProductRejectPosition;

    private String ProductRejectReason;

    private String ProductID;

    private LocalDateTime ProductDateTime;

    private String BatchNumber;

    private LocalDateTime DbCreateTime;
}