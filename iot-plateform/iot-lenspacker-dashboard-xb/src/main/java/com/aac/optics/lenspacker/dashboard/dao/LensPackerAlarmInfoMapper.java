package com.aac.optics.lenspacker.dashboard.dao;

import com.aac.optics.lenspacker.dashboard.entity.LensPackerAlarmInfo;
import com.aac.optics.lenspacker.dashboard.entity.ValueStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LensPackerAlarmInfoMapper extends BaseMapper<LensPackerAlarmInfo> {
    List<LensPackerAlarmInfo> getMachineAlarmDetail(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<LensPackerAlarmInfo> getMachineAlarmCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<LensPackerAlarmInfo> getMachineNameList();
}