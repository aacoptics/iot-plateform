package com.aac.optics.auth.server.service;

import com.aac.optics.auth.server.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IRoleService {

    Set<Role> queryUserRolesByUserId(Long userId);

}
