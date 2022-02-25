package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProgramDetailService extends IService<ProgramDetail> {

    List<ProgramDetail> getLastDayOee(String startTime);

    ProgramDetail getAbnormalTotalTime(String toolCode);

    List<ProgramDetail> getToolHisList(String toolCode);

    Map<String, String> getLastDayOEEByType(String type);
}