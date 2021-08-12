package com.aac.optics.fanuc.dashboard.service;

import com.aac.optics.fanuc.dashboard.entity.FanucMonitData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FanucMonitDataService extends IService<FanucMonitData> {
    List<FanucMonitData> getMonitDataByTime(String startTime, String endTime, String machineName);
}
