package com.aac.optics.lenspacker.dashboard.service;

import com.aac.optics.lenspacker.dashboard.entity.ValueStream;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ValueStreamService extends IService<ValueStream> {
    List<ValueStream> getMachineAlarmDetail(String startTime, String endTime);

    List<ValueStream> getMachineAlarmCount(String startTime, String endTime);

    List<ValueStream> getMachineCapacity(String startTime, String endTime);
}
