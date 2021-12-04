package com.aac.optics.perms.server.provider;

import com.aac.optics.common.core.vo.Result;
import com.aac.optics.common.web.entity.ResourceDefinition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Set;

@Component
@FeignClient(name = "iot-provider-organization", fallback = ResourceProviderFallback.class)
public interface ResourceProvider {

    @GetMapping(value = "/resource/all")
    Result<Set<ResourceDefinition>> resources();

    @GetMapping(value = "/resource/user/{username}")
    Result<Set<ResourceDefinition>> resources(@PathVariable("username") String username);
}
