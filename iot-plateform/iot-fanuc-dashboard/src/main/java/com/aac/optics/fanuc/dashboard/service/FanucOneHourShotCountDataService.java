package com.aac.optics.fanuc.dashboard.service;

import com.aac.optics.fanuc.dashboard.entity.FanucAlarmData;
import com.aac.optics.fanuc.dashboard.entity.FanucOneHourShotCountData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FanucOneHourShotCountDataService extends IService<FanucOneHourShotCountData> {
    List<FanucOneHourShotCountData> getUPH();
}
