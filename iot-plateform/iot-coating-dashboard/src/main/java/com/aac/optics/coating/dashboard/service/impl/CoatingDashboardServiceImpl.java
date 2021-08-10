package com.aac.optics.coating.dashboard.service.impl;

import com.aac.optics.coating.dashboard.dao.LensCoatingMachineNoodDataMapper;
import com.aac.optics.coating.dashboard.entity.CoatingStatusEntities;
import com.aac.optics.coating.dashboard.entity.CoatingStatusEntity;
import com.aac.optics.coating.dashboard.entity.LensCoatingMachineNoodData;
import com.aac.optics.coating.dashboard.service.CoatingDashboardService;
import com.aac.optics.coating.dashboard.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CoatingDashboardServiceImpl implements CoatingDashboardService {

    @Autowired
    LensCoatingMachineNoodDataMapper lensCoatingMachineNoodDataMapper;

    @Value("${coating.web-api-url}")
    private String webApiUrl;

    @Override
    public CoatingStatusEntities getCoatingMachineStatus() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse("2021-07-06 07:00:00", df);
        List<LensCoatingMachineNoodData> nodeDataList = lensCoatingMachineNoodDataMapper.getMachineTotalCount(ldt);
        JSONObject statusRes = HttpClientUtil.doGet(webApiUrl + "/Opc/GetAllLoadingStatus");
        CoatingStatusEntities allMachineStatus = JSONObject.toJavaObject(statusRes, CoatingStatusEntities.class);
        if (allMachineStatus.getSite() != null && allMachineStatus.getSite().size() > 0) {
            for (CoatingStatusEntity coatingStatusEntity : allMachineStatus.getSite()) {
                Optional<LensCoatingMachineNoodData> nodeDataOpt = nodeDataList.stream()
                        .filter(item -> item.getMachineId().equals(coatingStatusEntity.getName()))
                        .findFirst();
                LensCoatingMachineNoodData nodeData = nodeDataOpt.orElse(null);
                if (nodeData == null) {
                    coatingStatusEntity.setTotalNums(0);
                } else {
                    coatingStatusEntity.setTotalNums(nodeData.getTotalNums());
                }
            }
        }
        return allMachineStatus;
    }

    @Override
    public JSONObject getCoatingMachineAlarmInfo() {
        return HttpClientUtil.doGet(webApiUrl + "/Opc/GetAllAlarmCode");
    }
}
