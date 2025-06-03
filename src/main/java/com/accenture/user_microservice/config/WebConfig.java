package com.accenture.user_microservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminRoleInterceptor adminRoleInterceptor;
    private final InternalCallInterceptor internalCallInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminRoleInterceptor)
                .addPathPatterns("/api/user/admin/**")
                .excludePathPatterns("/api/user/admin/internal-use/**");

        registry.addInterceptor(internalCallInterceptor)
                .addPathPatterns("/api/user/admin/internal-use/**");
    }
}
