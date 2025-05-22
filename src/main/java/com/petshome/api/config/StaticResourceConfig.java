package com.petshome.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * 静态资源配置类
 */
@Configuration
@Slf4j
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${petshome.upload.local.upload-dir:uploads}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        log.info("静态资源配置初始化");
        log.info("上传目录: {}", uploadDir);
        String absolutePath = Paths.get(uploadDir).toAbsolutePath().toString();
        log.info("上传目录绝对路径: {}", absolutePath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(uploadDir).toAbsolutePath().toString();
        log.info("配置静态资源映射: /uploads/** -> file:{}/", absolutePath);

        // 配置不带/api前缀的路径
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath + "/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));

        log.info("配置静态资源映射: /api/uploads/** -> file:{}/", absolutePath);
        
        // 配置带/api前缀的路径
        registry.addResourceHandler("/api/uploads/**")
                .addResourceLocations("file:" + absolutePath + "/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));
    }
}
