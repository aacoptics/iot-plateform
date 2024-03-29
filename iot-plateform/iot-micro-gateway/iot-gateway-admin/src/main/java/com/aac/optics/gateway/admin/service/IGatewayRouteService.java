package com.aac.optics.gateway.admin.service;

import com.aac.optics.gateway.admin.entity.ov.GatewayRouteVo;
import com.aac.optics.gateway.admin.entity.param.GatewayRouteQueryParam;
import com.aac.optics.gateway.admin.entity.po.GatewayRoute;

import java.util.List;

public interface IGatewayRouteService {
    /**
     * 获取网关路由
     *
     * @param id
     * @return
     */
    GatewayRoute get(Long id);

    /**
     * 新增网关路由
     *
     * @param gatewayRoute
     * @return
     */
    boolean add(GatewayRoute gatewayRoute);

    /**
     * 查询网关路由
     *
     * @return
     */
    List<GatewayRouteVo> query(GatewayRouteQueryParam gatewayRouteQueryParam);

    /**
     * 更新网关路由信息
     *
     * @param gatewayRoute
     */
    boolean update(GatewayRoute gatewayRoute);

    /**
     * 根据id删除网关路由
     *
     * @param id
     */
    boolean delete(Long id);

    /**
     * 重新加载网关路由配置到redis
     *
     * @return 成功返回true
     */
    boolean overload();
}
