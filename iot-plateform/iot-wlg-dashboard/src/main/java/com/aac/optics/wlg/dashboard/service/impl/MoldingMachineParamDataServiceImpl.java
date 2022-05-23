package com.aac.optics.wlg.dashboard.service.impl;

import com.aac.optics.wlg.dashboard.entity.MoldingMachineParamData;
import com.aac.optics.wlg.dashboard.mapper.MoldingMachineParamDataMapper;
import com.aac.optics.wlg.dashboard.service.MoldingMachineParamDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class MoldingMachineParamDataServiceImpl  extends ServiceImpl<MoldingMachineParamDataMapper, MoldingMachineParamData> implements MoldingMachineParamDataService {
    @Resource
    private MoldingMachineParamDataMapper moldingMachineParamDataMapper;

    @Override
    public List<MoldingMachineParamData> getMoldingParamData(String machineName,
                                                      String paramName,
                                                      List<String> waferIds) {
        return moldingMachineParamDataMapper.getMoldingParamData(machineName, paramName, waferIds);
    }
}