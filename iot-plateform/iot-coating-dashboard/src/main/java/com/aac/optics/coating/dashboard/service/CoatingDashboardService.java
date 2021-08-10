package com.aac.optics.coating.dashboard.service;

import com.aac.optics.coating.dashboard.entity.CoatingStatusEntities;
import com.alibaba.fastjson.JSONObject;

public interface CoatingDashboardService {

    CoatingStatusEntities getCoatingMachineStatus();

    JSONObject getCoatingMachineAlarmInfo();

}
