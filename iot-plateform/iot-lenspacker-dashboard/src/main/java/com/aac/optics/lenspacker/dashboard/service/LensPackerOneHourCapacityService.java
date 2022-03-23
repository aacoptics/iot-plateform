package com.aac.optics.lenspacker.dashboard.service;

import com.aac.optics.lenspacker.dashboard.entity.LensPackerOneHourCapacity;
import com.aac.optics.lenspacker.dashboard.entity.ValueStream;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LensPackerOneHourCapacityService extends IService<LensPackerOneHourCapacity> {

    List<LensPackerOneHourCapacity> getTotalUph();

    List<LensPackerOneHourCapacity> getMachineCapacity(String startTime, String endTime);
}
