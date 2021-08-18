package com.aac.optics.fanuc.dashboard.service;

import com.aac.optics.fanuc.dashboard.entity.FanucDataEntity;
import com.aac.optics.fanuc.dashboard.entity.FanucDigitalData;
import com.aac.optics.fanuc.dashboard.entity.RealFanucStatusInfo;

import java.util.List;
import java.util.Map;

public interface FanucDashboardService {

    FanucDataEntity getDetailInfo(String machineName);

    List<RealFanucStatusInfo> getByFloor();

    List<FanucDigitalData> getDigitalData();

    Map<String, Map<String, Integer>> getStatusCount();

    Map<String, String> getCurrentOeeData();

}
