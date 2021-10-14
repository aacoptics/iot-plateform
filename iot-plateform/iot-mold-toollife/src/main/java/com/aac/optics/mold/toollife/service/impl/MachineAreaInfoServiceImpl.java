package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.dao.MachineAreaInfoMapper;
import com.aac.optics.mold.toollife.dao.ProgramDetailMapper;
import com.aac.optics.mold.toollife.entity.AreaConfig;
import com.aac.optics.mold.toollife.entity.MachineAreaInfo;
import com.aac.optics.mold.toollife.entity.ProgramDetail;
import com.aac.optics.mold.toollife.service.MachineAreaInfoService;
import com.aac.optics.mold.toollife.service.ProgramDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MachineAreaInfoServiceImpl extends ServiceImpl<MachineAreaInfoMapper, MachineAreaInfo> implements MachineAreaInfoService {

    @Override
    public AreaConfig getAreaConfig() {
        AreaConfig areaConfig = new AreaConfig();
        Map<String, String> areaInfo = new HashMap<>();
        Set<String> areaCode = new HashSet<>();
        List<MachineAreaInfo> machineAreaInfos = this.list();
        for (MachineAreaInfo machineAreaInfo : machineAreaInfos) {
            areaInfo.put(machineAreaInfo.getMachineName(), machineAreaInfo.getArea());
            areaCode.add(machineAreaInfo.getArea());
        }
        areaConfig.setAreaInfo(areaInfo).setAreaCode(areaCode);
        return areaConfig;
    }
}