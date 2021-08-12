package com.aac.optics.fanuc.dashboard.dao;

import com.aac.optics.fanuc.dashboard.entity.FanucMonitData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FanucMonitDataMapper extends BaseMapper<FanucMonitData> {
}