package com.aac.optics.gateway.web.service.impl;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.gateway.web.service.IAuthService;
import com.aac.optics.gateway.web.service.IPermissionService;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PermissionService implements IPermissionService {

    /**
     * 由authentication-client模块提供签权的feign客户端
     */
    @Autowired
    private IAuthService authService;

    @Override
    @Cached(name = "gateway_auth::", key = "#authentication+#method+#url",
            cacheType = CacheType.REMOTE, expire = 10, localLimit = 10000)
    public Result permission(String authentication, String url, String method) {
        return authService.hasPermission(authentication, url, method);
    }
}