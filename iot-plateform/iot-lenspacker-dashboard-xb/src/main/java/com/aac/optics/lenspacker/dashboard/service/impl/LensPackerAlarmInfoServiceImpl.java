package com.aac.optics.lenspacker.dashboard.service.impl;

import com.aac.optics.lenspacker.dashboard.dao.LensPackerAlarmInfoMapper;
import com.aac.optics.lenspacker.dashboard.dao.ValueStreamMapper;
import com.aac.optics.lenspacker.dashboard.entity.LensPackerAlarmInfo;
import com.aac.optics.lenspacker.dashboard.entity.ValueStream;
import com.aac.optics.lenspacker.dashboard.service.LensPackerAlarmInfoService;
import com.aac.optics.lenspacker.dashboard.service.ValueStreamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LensPackerAlarmInfoServiceImpl extends ServiceImpl<LensPackerAlarmInfoMapper, LensPackerAlarmInfo> implements LensPackerAlarmInfoService {

    @Autowired
    LensPackerAlarmInfoMapper lensPackerAlarmInfoMapper;

    @Override
    public List<LensPackerAlarmInfo> getMachineAlarmDetail(String startTime, String endTime){
        return lensPackerAlarmInfoMapper.getMachineAlarmDetail(startTime, endTime);
    }

    @Override
    public List<LensPackerAlarmInfo> getMachineAlarmCount(String startTime, String endTime){
        return lensPackerAlarmInfoMapper.getMachineAlarmCount(startTime, endTime);
    }

    @Override
    public List<LensPackerAlarmInfo> getMachineNameList(){
        return lensPackerAlarmInfoMapper.getMachineNameList();
    }
}
