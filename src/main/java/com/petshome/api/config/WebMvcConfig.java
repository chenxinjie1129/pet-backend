package com.petshome.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;

/**
 * Web MVC 配置
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${petshome.upload.local.upload-dir:uploads}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        log.info("上传目录配置: {}", uploadDir);
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().toString();
        log.info("上传目录绝对路径: {}", uploadPath);
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 配置静态资源映射
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().toString();
        log.info("配置静态资源映射: /api/uploads/** -> {}", uploadPath);

        // 添加上传文件的资源映射
        registry.addResourceHandler("/api/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // 添加不带/api前缀的映射，以防万一
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
