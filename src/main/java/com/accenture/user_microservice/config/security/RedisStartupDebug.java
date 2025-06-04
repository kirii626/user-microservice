package com.accenture.user_microservice.config.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisStartupDebug {

    @Value("${spring.redis.host}")
    private String redisHost;

    @PostConstruct
    public void logRedisHost() {
        System.out.println("🔍 spring.redis.host en tiempo de ejecución: " + redisHost);
    }
}
