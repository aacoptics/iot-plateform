package com.aac.optics.auth.server.service.impl;

import com.aac.optics.auth.server.entity.Role;
import com.aac.optics.auth.server.provider.OrganizationProvider;
import com.aac.optics.auth.server.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private OrganizationProvider organizationProvider;

    @Override
    public Set<Role> queryUserRolesByUserId(Long userId) {
        return organizationProvider.queryRolesByUserId(userId).getData();
    }

}
