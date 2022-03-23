package com.aac.optics.lenspacker.dashboard.service.impl;

import com.aac.optics.lenspacker.dashboard.dao.LensInfoMapper;
import com.aac.optics.lenspacker.dashboard.dao.LensPackerOneHourCapacityMapper;
import com.aac.optics.lenspacker.dashboard.entity.*;
import com.aac.optics.lenspacker.dashboard.service.LensInfoService;
import com.aac.optics.lenspacker.dashboard.service.LensPackerDashboardService;
import com.aac.optics.lenspacker.dashboard.service.LensPackerOneHourCapacityService;
import com.aac.optics.lenspacker.dashboard.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LensPackerOneHourCapacityServiceImpl extends ServiceImpl<LensPackerOneHourCapacityMapper, LensPackerOneHourCapacity> implements LensPackerOneHourCapacityService {
    @Autowired
    private LensPackerOneHourCapacityMapper lensPackerOneHourCapacityMapper;

    @Override
    public List<LensPackerOneHourCapacity> getTotalUph() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd 08:00:00");
        if (currentTime.getHour() < 8) {
            currentTime = currentTime.plusDays(-1);
        }
        String startTime = df.format(currentTime);
        return lensPackerOneHourCapacityMapper.getTotalUph(startTime);
    }

    @Override
    public List<LensPackerOneHourCapacity> getMachineCapacity(String startTime, String endTime){
        return lensPackerOneHourCapacityMapper.getMachineCapacity(startTime, endTime);
    }
}
