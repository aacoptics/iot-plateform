package com.aac.optics.mold.toollife.dao;

import com.aac.optics.mold.toollife.entity.EquipInfo;
import com.aac.optics.mold.toollife.entity.ToolInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EquipInfoMapper extends BaseMapper<EquipInfo> {

    List<EquipInfo> getMachineNames();
}
