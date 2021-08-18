package com.aac.optics.general.dashboard.service;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.general.dashboard.entity.LensPackerAlarmInfo;

import java.util.List;

public interface GeneralDashboardService {

    Result getGeneralStatusCount();

    List<LensPackerAlarmInfo> getGeneralCurrentAlarm();
}
