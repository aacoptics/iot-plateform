package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.EquipInfo;
import com.aac.optics.mold.toollife.entity.ToolInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;


public interface EquipInfoService extends IService<EquipInfo> {
    List<EquipInfo> getMachineNames();
}