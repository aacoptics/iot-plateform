package com.aac.optics.fanuc.dashboard.service;

import com.aac.optics.fanuc.dashboard.entity.FanucAlarmData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FanucAlarmDataService extends IService<FanucAlarmData> {
    List<FanucAlarmData> getAlarmDataByTime(String startTime, String endTime, String machineName);
}
