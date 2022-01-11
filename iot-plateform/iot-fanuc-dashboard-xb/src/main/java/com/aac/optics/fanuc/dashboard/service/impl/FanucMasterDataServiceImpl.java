package com.aac.optics.fanuc.dashboard.service.impl;

import com.aac.optics.fanuc.dashboard.dao.FanucAlarmDataMapper;
import com.aac.optics.fanuc.dashboard.dao.FanucMasterDataMapper;
import com.aac.optics.fanuc.dashboard.entity.FanucAlarmData;
import com.aac.optics.fanuc.dashboard.entity.FanucMasterData;
import com.aac.optics.fanuc.dashboard.service.FanucAlarmDataService;
import com.aac.optics.fanuc.dashboard.service.FanucMasterDataService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@DS("fanucDB")
public class FanucMasterDataServiceImpl extends ServiceImpl<FanucMasterDataMapper, FanucMasterData> implements FanucMasterDataService {

    @Resource
    FanucMasterDataMapper fanucMasterDataMapper;

    @Override
    public List<String> selectEquipNames() {
        LambdaQueryWrapper<FanucMasterData> lambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(FanucMasterData::getMachineName);
        return fanucMasterDataMapper.selectObjs(lambdaQueryWrapper).stream()
                .map(o -> (String) o)
                .collect(Collectors.toList());
    }
}
