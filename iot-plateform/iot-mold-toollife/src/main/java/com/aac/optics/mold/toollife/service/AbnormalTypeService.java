package com.aac.optics.mold.toollife.service;

import com.aac.optics.mold.toollife.entity.AbnormalType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface AbnormalTypeService extends IService<AbnormalType> {

    List<AbnormalType> getAbnormalType();


}