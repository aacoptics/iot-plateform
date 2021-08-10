package com.aac.optics.lenspacker.dashboard.service.impl;


import com.aac.optics.lenspacker.dashboard.entity.AlarmData;
import com.aac.optics.lenspacker.dashboard.entity.StatusData;
import com.aac.optics.lenspacker.dashboard.service.LensPackerDashboardService;
import com.aac.optics.lenspacker.dashboard.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
}
