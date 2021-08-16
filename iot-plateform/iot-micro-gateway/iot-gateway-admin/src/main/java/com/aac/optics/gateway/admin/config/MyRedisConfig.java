package com.aac.optics.gateway.admin.config;

import com.aac.optics.common.web.config.RedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class MyRedisConfig extends RedisConfig {
}