package com.aac.optics.lenspacker.dashboard.service;

import com.aac.optics.lenspacker.dashboard.entity.LensPackerAlarmInfo;
import com.aac.optics.lenspacker.dashboard.entity.ValueStream;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LensPackerAlarmInfoService extends IService<LensPackerAlarmInfo> {
    List<LensPackerAlarmInfo> getMachineAlarmDetail(String startTime, String endTime);

    List<LensPackerAlarmInfo> getMachineAlarmCount(String startTime, String endTime);

    List<LensPackerAlarmInfo> getMachineNameList();
}
