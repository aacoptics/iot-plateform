package com.aac.optics.wlg.report.service;


import com.aac.optics.wlg.report.entity.param.ProductionDayReportQueryParam;
import com.aac.optics.wlg.report.entity.param.ProductionMonthReportQueryParam;
import com.aac.optics.wlg.report.entity.param.ProductionProjectReportQueryParam;
import com.alibaba.fastjson.JSONArray;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProductionReportService {

    /**
     * 根据条件查询生产月度汇总数据
     *
     * @return
     */
    List<Map<String, Object>> queryProductionMonthReportByCondition(ProductionMonthReportQueryParam productionMonthReportQueryParam);


    /**
     * 根据条件查询月度汇总数据标题
     *
     * @return
     */
    JSONArray queryProductionMonthReportTitleByCondition(ProductionMonthReportQueryParam productionMonthReportQueryParam);


    /**
     * 根据条件查询生产日报表数据
     *
     * @return
     */
    List<Map<String, Object>> queryProductionDayReportByCondition(ProductionDayReportQueryParam productionDayReportQueryParam);



    /**
     * 根据条件查询单个项目报表表头
     *
     * @return
     */
    JSONArray queryProductionProjectReportTitleByCondition(ProductionProjectReportQueryParam productionProjectReportQueryParam);

    /**
     * 根据条件查询单个项目生产报表数据
     *
     * @return
     */
    List<Map<String, Object>> queryProductionProjectReportByCondition(ProductionProjectReportQueryParam productionProjectReportQueryParam);


    /**
     * 查询需要推送到钉钉的生产数据
     *
     * @return
     */
    List<Map<String, Object>> queryProductionDayDataByDate(LocalDate monthStart, LocalDate productionDate);


    /**
     * 获取区间日期
     * @return
     */
    List<String> findProductionReportDateByCondition(String projectName, String mold, String cycle, LocalDate dateStart, LocalDate dateEnd);


    /**
     * 查询需要以表格图片形式推送到钉钉的生产数据
     *
     * @return
     */
    List<Map<String, Object>> findProductionDayReportDataByDate(LocalDate monthStart, LocalDate monthEnd, LocalDate productionDate);

}
