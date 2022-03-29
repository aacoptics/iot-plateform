package com.aac.optics.wlg.report.service.impl;

import com.aac.optics.wlg.report.entity.param.ProductionDayReportQueryParam;
import com.aac.optics.wlg.report.entity.param.ProductionMonthReportQueryParam;
import com.aac.optics.wlg.report.exception.BusinessException;
import com.aac.optics.wlg.report.mapper.ProductionReportMapper;
import com.aac.optics.wlg.report.service.ProductionReportService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductionReportServiceImpl implements ProductionReportService {

    @Autowired
    private ProductionReportMapper productionReportMapper;


    @Override
    public List<Map<String, Object>> queryProductionMonthReportByCondition(ProductionMonthReportQueryParam productionMonthReportQueryParam) {
        String projectName = productionMonthReportQueryParam.getProjectName();

        LocalDate dateStart = productionMonthReportQueryParam.getDateStart();
        LocalDate dateEnd = productionMonthReportQueryParam.getDateEnd();
        //获取行转列表头
        List<String> reportDateList = productionReportMapper.findProductionReportDateByCondition(projectName,
                dateStart, dateEnd);
        if (reportDateList == null || reportDateList.size() == 0) {
            throw new BusinessException("所选条件不存在数据，请确认");
        }

        StringBuffer selectDateColumn = new StringBuffer();
        StringBuffer selectColumn = new StringBuffer();
        StringBuffer pivotIn = new StringBuffer();
        for (int i = 0; i < reportDateList.size(); i++) {
            String reportDate = reportDateList.get(i);
            if (i == 0) {
                selectDateColumn.append("[" + reportDate + "] as '" + reportDate + "'");
                selectColumn.append("sum([" + reportDate + "]) as '" + reportDate + "'");
                pivotIn.append("[" + reportDate + "]");
            } else {
                selectDateColumn.append(",[" + reportDate + "] as '" + reportDate + "'");
                selectColumn.append(", sum([" + reportDate + "]) as '" + reportDate + "'");
                pivotIn.append(", [" + reportDate + "]");
            }
        }
        List<Map<String, Object>> productionMonthList = productionReportMapper.findProductionMonthReportByCondition(
                projectName,
                selectDateColumn.toString(),
                selectColumn.toString(),
                pivotIn.toString(),
                dateStart,
                dateEnd);

        return productionMonthList;

    }


    @Override
    public JSONArray queryProductionMonthReportTitleByCondition(ProductionMonthReportQueryParam productionMonthReportQueryParam) {

        String projectName = productionMonthReportQueryParam.getProjectName();

        LocalDate dateStart = productionMonthReportQueryParam.getDateStart();
        LocalDate dateEnd = productionMonthReportQueryParam.getDateEnd();
        //获取行转列表头
        List<String> planDateList = productionReportMapper.findProductionReportDateByCondition(projectName,
                dateStart, dateEnd);

        JSONArray tableColumnJsonArray = new JSONArray();

        JSONObject seqJsonObject = new JSONObject();
        seqJsonObject.put("prop", "seq");
        seqJsonObject.put("label", "序号");
        seqJsonObject.put("fixed", "left");
        seqJsonObject.put("minWidth", "80");
        tableColumnJsonArray.add(seqJsonObject);

        JSONObject projectNameJsonObject = new JSONObject();
        projectNameJsonObject.put("prop", "projectName");
        projectNameJsonObject.put("label", "项目");
        projectNameJsonObject.put("fixed", "left");
        projectNameJsonObject.put("minWidth", "80");
        tableColumnJsonArray.add(projectNameJsonObject);

        JSONObject codeJsonObject = new JSONObject();
        codeJsonObject.put("prop", "code");
        codeJsonObject.put("label", "条件代码");
        codeJsonObject.put("fixed", "left");
        codeJsonObject.put("minWidth", "150");
        tableColumnJsonArray.add(codeJsonObject);

        JSONObject statusJsonObject = new JSONObject();
        statusJsonObject.put("prop", "name");
        statusJsonObject.put("label", "项目2");
        statusJsonObject.put("fixed", "left");
        statusJsonObject.put("minWidth", "160");
        tableColumnJsonArray.add(statusJsonObject);


        JSONObject maxQtyJsonObject = new JSONObject();
        maxQtyJsonObject.put("prop", "sumQty");
        maxQtyJsonObject.put("label", "汇总");
        maxQtyJsonObject.put("fixed", "left");
        maxQtyJsonObject.put("minWidth", "100");
        tableColumnJsonArray.add(maxQtyJsonObject);

        for (String planDate : planDateList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("prop", planDate);
            jsonObject.put("label", planDate);
            jsonObject.put("minWidth", "110");
            tableColumnJsonArray.add(jsonObject);
        }

        return tableColumnJsonArray;
    }

    @Override
    public List<Map<String, Object>> queryProductionDayReportByCondition(ProductionDayReportQueryParam productionDayReportQueryParam) {

        String projectName = productionDayReportQueryParam.getProjectName();

        LocalDate dateStart = productionDayReportQueryParam.getDateStart();
        LocalDate dateEnd = productionDayReportQueryParam.getDateEnd();

        List<Map<String, Object>> productionDayList = productionReportMapper.findProductionDayReportByCondition(
                projectName,
                dateStart,
                dateEnd);

        return productionDayList;
    }
}
