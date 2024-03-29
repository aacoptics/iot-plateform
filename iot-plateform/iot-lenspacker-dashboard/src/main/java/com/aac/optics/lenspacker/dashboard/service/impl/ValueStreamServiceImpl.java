package com.aac.optics.lenspacker.dashboard.service.impl;

import com.aac.optics.lenspacker.dashboard.dao.ValueStreamMapper;
import com.aac.optics.lenspacker.dashboard.entity.ValueStream;
import com.aac.optics.lenspacker.dashboard.service.ValueStreamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ValueStreamServiceImpl extends ServiceImpl<ValueStreamMapper, ValueStream> implements ValueStreamService {

    @Autowired
    ValueStreamMapper valueStreamMapper;

    @Override
    public List<ValueStream> getMachineAlarmDetail(String startTime, String endTime){
        return valueStreamMapper.getMachineAlarmDetail(startTime, endTime);
    }

    @Override
    public List<ValueStream> getMachineAlarmCount(String startTime, String endTime){
        return valueStreamMapper.getMachineAlarmCount(startTime, endTime);
    }

    @Override
    public List<ValueStream> getMachineCapacity(String startTime, String endTime){
        return valueStreamMapper.getMachineCapacity(startTime, endTime);
    }
}
