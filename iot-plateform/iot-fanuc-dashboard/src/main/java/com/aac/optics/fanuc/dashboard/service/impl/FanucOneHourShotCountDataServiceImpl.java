package com.aac.optics.fanuc.dashboard.service.impl;

import com.aac.optics.fanuc.dashboard.dao.FanucAlarmDataMapper;
import com.aac.optics.fanuc.dashboard.dao.FanucOneHourShotCountDataMapper;
import com.aac.optics.fanuc.dashboard.entity.FanucAlarmData;
import com.aac.optics.fanuc.dashboard.entity.FanucOneHourShotCountData;
import com.aac.optics.fanuc.dashboard.service.FanucAlarmDataService;
import com.aac.optics.fanuc.dashboard.service.FanucOneHourShotCountDataService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@DS("fanucDB")
public class FanucOneHourShotCountDataServiceImpl extends ServiceImpl<FanucOneHourShotCountDataMapper, FanucOneHourShotCountData> implements FanucOneHourShotCountDataService {

    @Autowired
    FanucOneHourShotCountDataMapper fanucOneHourShotCountDataMapper;

    @Override
    public List<FanucOneHourShotCountData> getUPH() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd 08:00:00");
        if (currentTime.getHour() < 8) {
            currentTime = currentTime.plusDays(-1);
        }
        String startTime = df.format(currentTime);
        return fanucOneHourShotCountDataMapper.getUPH(startTime);
    }

    @Override
    public List<FanucOneHourShotCountData> getTotalUph() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd 08:00:00");
        if (currentTime.getHour() < 8) {
            currentTime = currentTime.plusDays(-1);
        }
        String startTime = df.format(currentTime);
        return fanucOneHourShotCountDataMapper.getTotalUph(startTime);
    }
}
