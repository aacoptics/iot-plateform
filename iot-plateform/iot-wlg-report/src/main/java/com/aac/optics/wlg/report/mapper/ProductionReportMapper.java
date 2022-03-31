package com.aac.optics.wlg.report.mapper;

import com.aac.optics.wlg.report.entity.po.ProductionPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProductionReportMapper extends BaseMapper<ProductionPlan> {

    List<String> findProductionReportDateByCondition(@Param("projectName") String projectName,
                                                     @Param("mold") String mold,
                                                     @Param("cycle") String cycle,
                                                     @Param("dateStart") LocalDate dateStart,
                                                     @Param("dateEnd") LocalDate dateEnd);


    List<Map<String, Object>> findProductionMonthReportByCondition(@Param("projectName") String projectName,
                                                                   @Param("selectDateColumn") String selectDateColumn,
                                                                   @Param("selectColumn") String selectColumn,
                                                                   @Param("pivotIn") String pivotIn,
                                                                   @Param("dateStart") LocalDate dateStart,
                                                                   @Param("dateEnd") LocalDate dateEnd);

    List<Map<String, Object>> findProductionDayReportByCondition(@Param("projectName") String projectName,
                                                                 @Param("dateStart") LocalDate dateStart,
                                                                 @Param("dateEnd") LocalDate dateEnd);

    List<Map<String, Object>> findProductionProjectReportByCondition(@Param("projectName") String projectName,
                                                                     @Param("mold") String mold,
                                                                     @Param("cycle") String cycle,
                                                                     @Param("selectDateColumn") String selectDateColumn,
                                                                     @Param("selectColumn") String selectColumn,
                                                                     @Param("pivotIn") String pivotIn,
                                                                     @Param("dateStart") LocalDate dateStart,
                                                                     @Param("dateEnd") LocalDate dateEnd);
}
