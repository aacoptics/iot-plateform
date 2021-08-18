package com.aac.optics.general.dashboard.service.impl;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.general.dashboard.entity.LensPackerAlarmInfo;
import com.aac.optics.general.dashboard.provider.CoatingProvider;
import com.aac.optics.general.dashboard.provider.FanucProvider;
import com.aac.optics.general.dashboard.provider.LensPackerProvider;
import com.aac.optics.general.dashboard.service.GeneralDashboardService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class GeneralDashboardServiceImpl implements GeneralDashboardService {
    @Autowired
    private CoatingProvider coatingProvider;

    @Autowired
    private LensPackerProvider lensPackerProvider;

    @Autowired
    private FanucProvider fanucProvider;

    @Override
    public Result getGeneralStatusCount() {
        JSONArray lensPackerData = (JSONArray) lensPackerProvider.getStatusCount().getData();
        JSONArray fanucData = (JSONArray) fanucProvider.getStatusCount().getData();
        JSONArray coatingData = (JSONArray) coatingProvider.getStatusCount().getData();
        return Result.success();
    }

    @Override
    public List<LensPackerAlarmInfo> getGeneralCurrentAlarm() {
        JSONArray alarmInfo = (JSONArray)JSONArray.toJSON(lensPackerProvider.getCurrentAlarm().getData());
        List<LensPackerAlarmInfo> allAlarms = alarmInfo.toJavaList(LensPackerAlarmInfo.class);
        for (LensPackerAlarmInfo alarm : allAlarms) {
            Duration duration = Duration.between(alarm.getStartTime(), LocalDateTime.now());
            alarm.setDuration(duration.getSeconds());
        }
        return allAlarms;
    }
}
