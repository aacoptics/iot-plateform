package com.aac.optics.provider.organization.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aac.optics.provider.organization.dao.PositionMapper;
import com.aac.optics.provider.organization.entity.param.PositionQueryParam;
import com.aac.optics.provider.organization.entity.po.Position;
import com.aac.optics.provider.organization.service.IPositionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PositionService extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Override
    public boolean add(Position position) {
        return this.save(position);
    }

    @Override
    @CacheInvalidate(name = "position::", key = "#id")
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    @CacheInvalidate(name = "position::", key = "#position.id")
    public boolean update(Position position) {
        return this.updateById(position);
    }

    @Override
    @Cached(name = "position::", key = "#id", cacheType = CacheType.REMOTE)
    public Position get(Long id) {
        return this.getById(id);
    }

    @Override
    public List<Position> query(PositionQueryParam positionQueryParam) {
        QueryWrapper<Position> queryWrapper = positionQueryParam.build();
        queryWrapper.eq(StringUtils.isNotBlank(positionQueryParam.getName()), "name", positionQueryParam.getName());
        return this.list(queryWrapper);
    }
}
