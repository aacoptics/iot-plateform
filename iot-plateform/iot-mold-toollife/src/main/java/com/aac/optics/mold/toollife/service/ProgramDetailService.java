package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface ProgramDetailService extends IService<ProgramDetail> {

    List<ProgramDetail> getLastDayOee(String startTime);

    ProgramDetail getAbnormalTotalTime(String toolCode);
}