package com.aac.optics.coating.dashboard.service;

import com.aac.optics.coating.dashboard.entity.CoatingStatusEntities;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface CoatingDashboardService {

    CoatingStatusEntities getCoatingMachineStatus();

    JSONObject getCoatingMachineAlarmInfo();

    Map<String, Map<String, Integer>> getStatusCount();

}
