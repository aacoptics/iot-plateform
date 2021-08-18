package com.aac.optics.general.dashboard.service;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.general.dashboard.entity.LensPackerAlarmInfo;

import java.util.List;
import java.util.Map;

public interface GeneralDashboardService {

    Map<String, Integer> getGeneralStatusCount();

    List<LensPackerAlarmInfo> getGeneralCurrentAlarm();
}
