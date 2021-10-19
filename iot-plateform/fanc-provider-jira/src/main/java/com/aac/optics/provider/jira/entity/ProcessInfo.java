package com.aac.optics.provider.jira.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("tb_mold_program_detail")
public class ProcessInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String businessId;

    private String hardlEmp;

    private String questDes;

    private String status;

    private String hardlCreateTime;

    private String hardlFinishTime;
}