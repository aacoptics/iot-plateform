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
                                                                   @Param("selectVarcharColumn") String selectVarcharColumn,
                                                                   @Param("selectSumVarcharColumn") String selectSumVarcharColumn,
                                                                   @Param("selectRateColumn") String selectRateColumn,
                                                                   @Param("selectSumRateColumn") String selectSumRateColumn,
                                                                   @Param("selectJHCHANCHUColumn") String selectJHCHANCHUColumn,
                                                                   @Param("selectJHLINGLIAOColumn") String selectJHLINGLIAOColumn,
                                                                   @Param("selectJHHDCHANCHUColumn") String selectJHHDCHANCHUColumn,
                                                                   @Param("selectJHHDCHANCHUSumColumn") String selectJHHDCHANCHUSumColumn,
                                                                   @Param("selectJHCHANCHUSumColumn") String selectJHCHANCHUSumColumn,
                                                                   @Param("selectJHLINGLIAOSumColumn") String selectJHLINGLIAOSumColumn,
                                                                   @Param("dateStart") LocalDate dateStart,
                                                                   @Param("dateEnd") LocalDate dateEnd);

    List<Map<String, Object>> findProductionDayReportByCondition(@Param("projectName") String projectName,
                                                                 @Param("mold") String mold,
                                                                 @Param("cycle") String cycle,
                                                                 @Param("dateStart") LocalDate dateStart,
                                                                 @Param("dateEnd") LocalDate dateEnd);

    List<Map<String, Object>> findProductionProjectReportByCondition(@Param("projectName") String projectName,
                                                                     @Param("mold") String mold,
                                                                     @Param("cycle") String cycle,
                                                                     @Param("selectDateColumn") String selectDateColumn,
                                                                     @Param("selectColumn") String selectColumn,
                                                                     @Param("pivotIn") String pivotIn,
                                                                     @Param("selectVarcharColumn") String selectVarcharColumn,
                                                                     @Param("selectSumVarcharColumn") String selectSumVarcharColumn,
                                                                     @Param("selectJHCHANCHUColumn") String selectJHCHANCHUColumn,
                                                                     @Param("selectJHLINGLIAOColumn") String selectJHLINGLIAOColumn,
                                                                     @Param("selectJHHDCHANCHUColumn") String selectJHHDCHANCHUColumn,
                                                                     @Param("selectJHZHITONGLVColumn") String selectJHZHITONGLVColumn,
                                                                     @Param("selectSJLIANGLVColumn") String selectSJLIANGLVColumn,
                                                                     @Param("selectSJZHITONGLVColumn") String selectSJZHITONGLVColumn,
                                                                     @Param("selectDCLVColumn") String selectDCLVColumn,
                                                                     @Param("selectJHHDCHANCHUSumColumn") String selectJHHDCHANCHUSumColumn,
                                                                     @Param("selectJHCHANCHUSumColumn") String selectJHCHANCHUSumColumn,
                                                                     @Param("selectJHLINGLIAOSumColumn") String selectJHLINGLIAOSumColumn,
                                                                     @Param("dateStart") LocalDate dateStart,
                                                                     @Param("dateEnd") LocalDate dateEnd);

    List<Map<String, Object>> findProductionDayDataByDate(
            @Param("monthStart") LocalDate monthStart,
            @Param("productionDate") LocalDate productionDate);

}
