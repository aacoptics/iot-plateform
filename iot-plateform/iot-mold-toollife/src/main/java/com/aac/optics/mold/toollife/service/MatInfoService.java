package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.EquipInfo;
import com.aac.optics.mold.toollife.entity.MatInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface MatInfoService extends IService<MatInfo> {
    List<MatInfo> getMatInfo();
}