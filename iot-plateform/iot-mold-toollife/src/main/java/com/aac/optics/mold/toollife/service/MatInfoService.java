package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.EquipInfo;
import com.aac.optics.mold.toollife.entity.MatInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MatInfoService extends IService<MatInfo> {
    List<MatInfo> getMatInfo();

    Integer getScrapCount(String startTime);

    Integer getOutCount(String startTime);

    Double getScrapRate(String startTime);
}