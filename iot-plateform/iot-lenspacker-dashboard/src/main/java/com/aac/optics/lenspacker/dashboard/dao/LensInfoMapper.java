package com.aac.optics.lenspacker.dashboard.dao;

import com.aac.optics.lenspacker.dashboard.entity.LensInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Mapper
public interface LensInfoMapper extends BaseMapper<LensInfo> {
    List<LensInfo> getLensPackerMachineInfo();
}