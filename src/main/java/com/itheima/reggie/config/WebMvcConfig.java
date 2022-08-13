package com.itheima.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Slf4j
@Configuration  //配置类静态资源默认static 访问不了静态方法，需要编写mvc静态资源映射
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源的映射。。。。。");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");//静态资源映射
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");//静态资源映射



    }
}
