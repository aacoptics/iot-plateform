package com.aac.optics.fanuc.dashboard.service;

import com.aac.optics.fanuc.dashboard.entity.FanucAlarmData;
import com.aac.optics.fanuc.dashboard.entity.FanucMasterData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FanucMasterDataService extends IService<FanucMasterData> {
    List<String> selectEquipNames();
}
