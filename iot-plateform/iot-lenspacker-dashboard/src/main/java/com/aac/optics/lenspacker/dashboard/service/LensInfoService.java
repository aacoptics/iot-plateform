package com.aac.optics.lenspacker.dashboard.service;

import com.aac.optics.lenspacker.dashboard.entity.LensInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LensInfoService extends IService<LensInfo> {
    List<LensInfo> getLensPackerMachineInfo();
}
