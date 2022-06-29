package com.aac.optics.wlg.report.mapper;

import com.aac.optics.wlg.report.entity.po.CustomerRequirement;
import com.aac.optics.wlg.report.entity.po.ProductionSummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;

@Repository
@Mapper
public interface ProductionSummaryMapper extends BaseMapper<ProductionSummary> {


    IPage<Map<String, Object>> queryProductionSummaryByCondition(@Param("page") Page page,
                                                           @Param("projectName") String projectName,
                                                           @Param("productionDateStart") LocalDate productionDateStart,
                                                           @Param("productionDateEnd") LocalDate productionDateEnd);

}
