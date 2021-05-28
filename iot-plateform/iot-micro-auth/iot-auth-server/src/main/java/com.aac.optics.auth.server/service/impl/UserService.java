package com.aac.optics.auth.server.service.impl;

import com.aac.optics.auth.server.entity.User;
import com.aac.optics.auth.server.provider.OrganizationProvider;
import com.aac.optics.auth.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private OrganizationProvider organizationProvider;

    @Override
    public User getByUniqueId(String uniqueId) {
        return organizationProvider.getUserByUniqueId(uniqueId).getData();
    }
}
