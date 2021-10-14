package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.AreaConfig;
import com.aac.optics.mold.toollife.entity.MachineAreaInfo;
import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MachineAreaInfoService extends IService<MachineAreaInfo> {

    AreaConfig getAreaConfig();
}