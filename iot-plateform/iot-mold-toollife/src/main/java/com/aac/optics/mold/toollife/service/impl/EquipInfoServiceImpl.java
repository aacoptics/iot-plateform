package com.aac.optics.mold.toollife.service.impl;

import com.aac.optics.mold.toollife.dao.EquipInfoMapper;
import com.aac.optics.mold.toollife.dao.ToolInfoMapper;
import com.aac.optics.mold.toollife.entity.EquipInfo;
import com.aac.optics.mold.toollife.entity.ToolInfo;
import com.aac.optics.mold.toollife.service.EquipInfoService;
import com.aac.optics.mold.toollife.service.ToolInfoService;
import com.aac.optics.mold.toollife.util.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Service
@Slf4j
public class EquipInfoServiceImpl extends ServiceImpl<EquipInfoMapper, EquipInfo> implements EquipInfoService {
    @Autowired
    EquipInfoMapper equipInfoMapper;

    @Override
    public List<EquipInfo> getMachineNames() {
        return equipInfoMapper.getMachineNames();
    }
}