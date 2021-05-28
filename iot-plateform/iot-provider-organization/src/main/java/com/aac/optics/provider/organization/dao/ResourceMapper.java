package com.aac.optics.provider.organization.dao;

import com.aac.optics.provider.organization.entity.po.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
}