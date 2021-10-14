package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.dao.ProgramDetailMapper;
import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.aac.optics.mold.toollife.service.ProgramDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProgramDetailServiceImpl extends ServiceImpl<ProgramDetailMapper, ProgramDetail> implements ProgramDetailService {

    @Autowired
    ProgramDetailMapper programDetailMapper;

    @Override
    public List<ProgramDetail> getLastDayOee(String startTime) {
        return programDetailMapper.getLastDayOee(startTime);
    }
}