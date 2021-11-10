package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.dao.AbnormalTypeMapper;
import com.aac.optics.mold.toollife.entity.AbnormalType;
import com.aac.optics.mold.toollife.service.AbnormalTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AbnormalTypeServiceImpl extends ServiceImpl<AbnormalTypeMapper, AbnormalType> implements AbnormalTypeService {

    @Override
    public List<AbnormalType> getAbnormalType() {
        QueryWrapper<AbnormalType> wrapper = new QueryWrapper<>();
        wrapper.eq("enable", 1);
        return list(wrapper);
    }
}