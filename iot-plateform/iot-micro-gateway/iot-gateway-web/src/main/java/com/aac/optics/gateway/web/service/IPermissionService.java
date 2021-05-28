package com.aac.optics.gateway.web.service;

import com.aac.optics.common.core.vo.Result;

public interface IPermissionService {
    /**
     * @param authentication
     * @param method
     * @param url
     * @return
     */
    Result permission(String authentication, String url, String method);
}
