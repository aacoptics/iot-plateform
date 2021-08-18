package com.aac.optics.lenspacker.dashboard.dao;

import com.aac.optics.lenspacker.dashboard.entity.LensInfo;
import com.aac.optics.lenspacker.dashboard.entity.LensPackerOneHourCapacity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LensPackerOneHourCapacityMapper extends BaseMapper<LensPackerOneHourCapacity> {
    List<LensPackerOneHourCapacity> getTotalUph(@Param("startTime")  String startTime);
}