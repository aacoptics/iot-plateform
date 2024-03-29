package com.aac.optics.lenspacker.dashboard.service;

import com.aac.optics.lenspacker.dashboard.entity.AlarmData;
import com.aac.optics.lenspacker.dashboard.entity.StatusData;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface LensPackerDashboardService {

    List<StatusData> getStatusInfo();

    List<AlarmData> getCurrentAlarmInfo();

    Map<String, Map<String, Integer>> getStatusCount();

}
