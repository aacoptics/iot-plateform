package com.aac.optics.lenspacker.dashboard.service.impl;

import com.aac.optics.lenspacker.dashboard.entity.AlarmData;
import com.aac.optics.lenspacker.dashboard.entity.StatusData;
import com.aac.optics.lenspacker.dashboard.service.LensPackerDashboardService;
import com.aac.optics.lenspacker.dashboard.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LensPackerDashboardServiceImpl implements LensPackerDashboardService {
    @Value("${lensPacker.web-api-url}")
    private String webApiUrl;

    @Override
    public List<StatusData> getStatusInfo() {
        JSONArray statusRes = HttpClientUtil.doGet(webApiUrl + "/Opc/GetAllStatus");
        List<StatusData> allMachineStatusData = statusRes.toJavaList(StatusData.class);
        allMachineStatusData = allMachineStatusData.stream().
                sorted((Comparator.comparing(StatusData::getMachineNo)))
                .collect(Collectors.toList());
        return allMachineStatusData;
    }

    @Override
    public List<AlarmData> getCurrentAlarmInfo() {
        JSONArray alarmRes = HttpClientUtil.doGet(webApiUrl + "/Opc/GetCurrentAlarms");
        List<AlarmData> allAlarms = alarmRes.toJavaList(AlarmData.class);
        return allAlarms;
    }

    @Override
    public Map<String, Map<String, Integer>> getStatusCount() {
        Map<String, Map<String, Integer>> lenspackerStatusCount = new HashMap<>();
        List<StatusData> allMachineStatusData = this.getStatusInfo();
        for (StatusData statusData : allMachineStatusData) {
            String floor = statusData.getMachineNo().substring(2, 4);
            if (!lenspackerStatusCount.containsKey(floor)) {
                Map<String, Integer> statusNums = new HashMap<>();
                lenspackerStatusCount.put(floor, statusNums);
            }
            String statusCode = statusData.getStatus().toString();
            if (!lenspackerStatusCount.get(floor).containsKey(statusCode)) {
                lenspackerStatusCount.get(floor).put(statusCode, 0);
            }
            lenspackerStatusCount.get(floor).put(statusCode, lenspackerStatusCount.get(floor).get(statusCode) + 1);
        }
        return lenspackerStatusCount;
    }
}
