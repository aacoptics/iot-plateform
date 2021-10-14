package com.aac.optics.mold.toollife.dao;

import com.aac.optics.mold.toollife.entity.MachineAreaInfo;
import com.aac.optics.mold.toollife.entity.ToolInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Mapper
public interface MachineAreaInfoMapper extends BaseMapper<MachineAreaInfo> {

}
