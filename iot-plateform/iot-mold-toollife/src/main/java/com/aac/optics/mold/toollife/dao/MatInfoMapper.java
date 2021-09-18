package com.aac.optics.mold.toollife.dao;

import com.aac.optics.mold.toollife.entity.EquipInfo;
import com.aac.optics.mold.toollife.entity.MatInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MatInfoMapper extends BaseMapper<MatInfo> {

    @DS("moldMes")
    List<MatInfo> getMatInfo();
}
