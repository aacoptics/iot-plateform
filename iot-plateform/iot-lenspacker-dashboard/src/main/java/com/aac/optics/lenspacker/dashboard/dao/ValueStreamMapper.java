package com.aac.optics.lenspacker.dashboard.dao;

import com.aac.optics.lenspacker.dashboard.entity.ValueStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Mapper
public interface ValueStreamMapper extends BaseMapper<ValueStream> {
    List<ValueStream> getMachineAlarmDetail(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<ValueStream> getMachineAlarmCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<ValueStream> getMachineCapacity(@Param("startTime") String startTime, @Param("endTime") String endTime);
}