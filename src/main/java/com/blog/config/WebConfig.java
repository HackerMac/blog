package com.blog.config;

import com.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {   //配置拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")       // 对于admin目录下的子目录的子目录..设置拦截
                .excludePathPatterns("/admin")      // 对于admin同目录下的文件设置拦截
                .excludePathPatterns("/admin/login");   // 对admin/login设置拦截无法直接访问
    }
}
