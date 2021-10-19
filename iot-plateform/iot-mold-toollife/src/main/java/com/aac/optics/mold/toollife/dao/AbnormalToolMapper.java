package com.aac.optics.mold.toollife.dao;

import com.aac.optics.mold.toollife.entity.AbnormalTool;
import com.aac.optics.mold.toollife.entity.ScrapedTool;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AbnormalToolMapper extends BaseMapper<AbnormalTool> {

}
