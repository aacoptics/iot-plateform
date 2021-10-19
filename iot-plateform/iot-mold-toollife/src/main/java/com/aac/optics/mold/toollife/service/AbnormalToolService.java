package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.AbnormalTool;
import com.aac.optics.mold.toollife.entity.ScrapedTool;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface AbnormalToolService extends IService<AbnormalTool> {

    void saveAbnormalTool();

    List<AbnormalTool> getAbnormalTools();

    void updateAbnormalReason(AbnormalTool abnormalTool);

    void updateConfirmInfo(AbnormalTool abnormalTool);

    Integer getAbnormalCount(String startTime, String endTime);
}