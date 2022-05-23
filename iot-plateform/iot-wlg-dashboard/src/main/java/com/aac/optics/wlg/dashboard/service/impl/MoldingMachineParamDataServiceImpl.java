package com.aac.optics.wlg.dashboard.service.impl;

import com.aac.optics.wlg.dashboard.entity.MoldingMachineParamData;
import com.aac.optics.wlg.dashboard.mapper.MoldingMachineParamDataMapper;
import com.aac.optics.wlg.dashboard.service.MoldingMachineParamDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MoldingMachineParamDataServiceImpl extends ServiceImpl<MoldingMachineParamDataMapper, MoldingMachineParamData> implements MoldingMachineParamDataService {
    @Resource
    private MoldingMachineParamDataMapper moldingMachineParamDataMapper;

    @Override
    public List<MoldingMachineParamData> getMoldingParamData(String machineName,
                                                             String paramName,
                                                             List<String> waferIds) {
        List<MoldingMachineParamData> moldingMachineParamData = moldingMachineParamDataMapper.getMoldingParamData(machineName, paramName, waferIds);
        if (moldingMachineParamData.size() == 0) {
            return null;
        }

        String tempWaferId = "0";
        LocalDateTime minDateTime = LocalDateTime.MIN;
        for (MoldingMachineParamData moldingMachineParamDatum : moldingMachineParamData) {
            if (!moldingMachineParamDatum.getWaferId().equals(tempWaferId)) {
                minDateTime = moldingMachineParamDatum.getPlcTime();
                tempWaferId = moldingMachineParamDatum.getWaferId();
            }
            Duration duration = Duration.between(minDateTime, moldingMachineParamDatum.getPlcTime());
            moldingMachineParamDatum.setPlcTimeStamp(duration.getSeconds());
        }
        return moldingMachineParamData;
    }

    @Override
    public List<MoldingMachineParamData> getMachineName() {
        return moldingMachineParamDataMapper.getMachineName();
    }

    @Override
    public List<MoldingMachineParamData> getWaferIds(String machineName,
                                                     LocalDateTime startTime,
                                                     LocalDateTime endTime) {
        return moldingMachineParamDataMapper.getWaferIds(machineName, startTime, endTime);
    }

    @Override
    public List<MoldingMachineParamData> getMoldingParamName(String machineName,
                                                             List<String> waferIds) {
        List<MoldingMachineParamData> moldingMachineParamData = moldingMachineParamDataMapper.getMoldingParamName(machineName, waferIds);
        return moldingMachineParamData;
    }
}