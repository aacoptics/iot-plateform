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
public interface ProductionPlanMapper extends BaseMapper<ProductionPlan> {

    List<String> findPlanDateByMonth(@Param("projectName") String projectName,
                                     @Param("mold") String mold,
                                     @Param("cycle") String cycle,
                                     @Param("planDateStart") LocalDate planDateStart,
                                     @Param("planDateEnd") LocalDate planDateEnd);


    List<Map<String, Object>> findProductionPlanByMonth(@Param("projectName") String projectName,
                                                        @Param("mold") String mold,
                                                        @Param("cycle") String cycle,
                                                      @Param("selectColumn") String selectColumn,
                                                      @Param("pivotIn") String pivotIn,
                                                      @Param("planDateStart") LocalDate planDateStart,
                                                      @Param("planDateEnd") LocalDate planDateEnd);


}
