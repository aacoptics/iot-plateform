package com.aac.optics.fanuc.dashboard.service;

import com.aac.optics.fanuc.dashboard.entity.FanucCondData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FanucCondDataService extends IService<FanucCondData> {
    List<FanucCondData> getCondDataByTime(String startTime, String endTime, String machineName);
}
