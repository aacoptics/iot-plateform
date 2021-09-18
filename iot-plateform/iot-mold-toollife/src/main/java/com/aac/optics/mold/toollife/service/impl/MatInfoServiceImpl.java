package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.dao.EquipInfoMapper;
import com.aac.optics.mold.toollife.dao.MatInfoMapper;
import com.aac.optics.mold.toollife.entity.EquipInfo;
import com.aac.optics.mold.toollife.entity.MatInfo;
import com.aac.optics.mold.toollife.service.EquipInfoService;
import com.aac.optics.mold.toollife.service.MatInfoService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@DS("moldMes")
public class MatInfoServiceImpl extends ServiceImpl<MatInfoMapper, MatInfo> implements MatInfoService {
    @Autowired
    MatInfoMapper matInfoMapper;

    @Override
    public List<MatInfo> getMatInfo() {
        return matInfoMapper.getMatInfo();
    }
}