package com.aac.optics.provider.organization.service.impl;

import com.aac.optics.common.web.entity.ResourceDefinition;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aac.optics.provider.organization.config.BusConfig;
import com.aac.optics.provider.organization.dao.ResourceMapper;
import com.aac.optics.provider.organization.entity.param.ResourceQueryParam;
import com.aac.optics.provider.organization.entity.po.Resource;
import com.aac.optics.provider.organization.entity.po.Role;
import com.aac.optics.provider.organization.entity.po.RoleResource;
import com.aac.optics.provider.organization.entity.po.User;
import com.aac.optics.provider.organization.events.EventSender;
import com.aac.optics.provider.organization.service.IResourceService;
import com.aac.optics.provider.organization.service.IRoleResourceService;
import com.aac.optics.provider.organization.service.IRoleService;
import com.aac.optics.provider.organization.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceService extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private IRoleResourceService roleResourceService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private EventSender eventSender;

    @Override
    public boolean add(Resource resource) {
        boolean isSuccess = this.save(resource);
        ResourceDefinition resourceDefinition = resourceToResourceDefinition(resource);
        eventSender.send(BusConfig.ROUTING_KEY, resourceDefinition);
        return isSuccess;
    }

    @Override
    @Cached(name = "resource::", key = "#id", cacheType = CacheType.REMOTE)
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    @Cached(name = "resource::", key = "#resource.id", cacheType = CacheType.REMOTE)
    public boolean update(Resource resource) {
        return this.updateById(resource);
    }

    @Override
    @Cached(name = "resource::", key = "#id", cacheType = CacheType.REMOTE)
    public Resource get(Long id) {
        return this.getById(id);
    }

    @Override
    public IPage<Resource> query(Page page, ResourceQueryParam resourceQueryParam) {
        QueryWrapper<Resource> queryWrapper = resourceQueryParam.build();
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getName()), "name", resourceQueryParam.getName());
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getType()), "type", resourceQueryParam.getType());
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getUrl()), "url", resourceQueryParam.getUrl());
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getMethod()), "method", resourceQueryParam.getMethod());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Resource> getAll() {
        return this.list();
    }

    @Override
    @Cached(name = "resource4user::", key = "#username", cacheType = CacheType.REMOTE)
    public List<Resource> query(String username) {
        //根据用户名查询到用户所拥有的角色
        User user = userService.getByUniqueId(username);
        List<Role> roles = roleService.query(user.getId());
        //提取用户所拥有角色id列表
        Set<Long> roleIds = roles.stream().map(role -> role.getId()).collect(Collectors.toSet());
        //根据角色列表查询到角色的资源的关联关系
        List<RoleResource> roleResources = roleResourceService.queryByRoleIds(roleIds);
        //根据资源列表查询出所有资源对象
        Set<Long> resourceIds = roleResources.stream().map(roleResource -> roleResource.getResourceId()).collect(Collectors.toSet());
        if(resourceIds.size() == 0)
            return new ArrayList<>();
        //根据resourceId列表查询出resource对象
        return (List<Resource>) this.listByIds(resourceIds);
    }

    @Override
    public List<Resource> query(Long roleId) {
        //根据资源列表查询出所有资源对象
        Set<Long> resourceIds = roleResourceService.queryByRoleId(roleId);
        if(resourceIds.size() == 0)
            return new ArrayList<>();
        //根据resourceId列表查询出resource对象
        return (List<Resource>) this.listByIds(resourceIds);
    }

    private ResourceDefinition resourceToResourceDefinition(Resource resource) {
        ResourceDefinition resourceDefinition = new ResourceDefinition();
        BeanUtils.copyProperties(resource, resourceDefinition);
        return resourceDefinition;
    }
}