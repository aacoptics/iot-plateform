package com.aac.optics.wlg.report.entity.po;

import com.aac.optics.common.web.entity.po.BasePo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("customer_requirement")
public class CustomerRequirement extends BasePo {

    /**
     * 项目
     */
    private String projectName;

    /**
     * 需求数量
     */
    private BigDecimal qty;

    /**
     * 达成率
     */
    private BigDecimal completionRate;


    /**
     * 需求月份
     */
    private LocalDate requirementDate;


}