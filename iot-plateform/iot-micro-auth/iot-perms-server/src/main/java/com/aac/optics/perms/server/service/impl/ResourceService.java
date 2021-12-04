package com.aac.optics.perms.server.service.impl;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.common.web.entity.ResourceDefinition;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.aac.optics.perms.server.provider.ResourceProvider;
import com.aac.optics.perms.server.service.IResourceService;
import com.aac.optics.perms.server.service.NewMvcRequestMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceService implements IResourceService {
    @Autowired
    private ResourceProvider resourceProvider;

    /**
     * 系统中所有权限集合
     */
    private static final Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes = new HashMap<>();

    @Override
    public synchronized void saveResource(ResourceDefinition resourceDefinition) {
        resourceConfigAttributes.put(
                new NewMvcRequestMatcher(new HandlerMappingIntrospector(), resourceDefinition.getUrl(), resourceDefinition.getMethod()),
                new SecurityConfig(resourceDefinition.getCode())
        );
        log.info("resourceConfigAttributes size:{}", resourceConfigAttributes.size());
    }

    @Override
    public synchronized void removeResource(ResourceDefinition resourceDefinition) {
        resourceConfigAttributes.remove(
                new NewMvcRequestMatcher(
                        new HandlerMappingIntrospector(), resourceDefinition.getUrl(), resourceDefinition.getMethod()));
        log.info("resourceConfigAttributes size:{}", resourceConfigAttributes.size());
    }

    @Override
    public synchronized void loadResource() {
        Result<Set<ResourceDefinition>> resourcesResult = resourceProvider.resources();
        if (resourcesResult.isFail()) {
            System.exit(1);
        }
        Set<ResourceDefinition> resourceDefinitions = resourcesResult.getData();
        Map<MvcRequestMatcher, SecurityConfig> tempResourceConfigAttributes = resourceDefinitions.stream()
                .collect(Collectors.toMap(
                        resourceDefinition -> new NewMvcRequestMatcher(new HandlerMappingIntrospector(), resourceDefinition.getUrl(), resourceDefinition.getMethod()),
                        resourceDefinition -> new SecurityConfig(resourceDefinition.getCode())
                ));
        resourceConfigAttributes.putAll(tempResourceConfigAttributes);
        log.debug("init resourceConfigAttributes:{}", resourceConfigAttributes);
    }

    @Override
    public ConfigAttribute findConfigAttributesByUrl(HttpServletRequest authRequest) {
        return resourceConfigAttributes.keySet().stream()
                .filter(requestMatcher -> requestMatcher.matches(authRequest))
                .map(requestMatcher -> resourceConfigAttributes.get(requestMatcher))
                .peek(urlConfigAttribute -> log.debug("url在资源池中配置：{}", urlConfigAttribute.getAttribute()))
                .findFirst()
                .orElse(new SecurityConfig("NONEXISTENT_URL"));
    }

    @Override
    @Cached(name = "resource4user::", key = "#username", cacheType = CacheType.REMOTE)
    public Set<ResourceDefinition> queryByUsername(String username) {
        return resourceProvider.resources(username).getData();
    }
}