package com.aac.optics.fanuc.dashboard.service;

import com.aac.optics.fanuc.dashboard.entity.FanucMonitData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FanucMonitDataService extends IService<FanucMonitData> {
    List<FanucMonitData> getMonitDataByTime(String startTime, String endTime, String machineName);

    List<FanucMonitData> getAllCycleList(LocalDateTime startTime, LocalDateTime endTime);
}
