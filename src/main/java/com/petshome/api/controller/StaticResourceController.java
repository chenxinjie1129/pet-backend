package com.petshome.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 静态资源控制器
 */
@Controller
@RequestMapping("/static")
@Slf4j
public class StaticResourceController {

    @Value("${petshome.upload.local.upload-dir:uploads}")
    private String uploadDir;

    /**
     * 获取图片资源
     * @param directory 目录
     * @param year 年
     * @param month 月
     * @param day 日
     * @param filename 文件名
     * @return 图片资源
     */
    @GetMapping("/image/{directory}/{year}/{month}/{day}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(
            @PathVariable String directory,
            @PathVariable String year,
            @PathVariable String month,
            @PathVariable String day,
            @PathVariable String filename) {
        
        log.info("请求图片: {}/{}/{}/{}/{}", directory, year, month, day, filename);
        
        // 构建文件路径
        String filePath = String.format("%s/%s/%s/%s/%s", directory, year, month, day, filename);
        Path path = Paths.get(uploadDir, filePath);
        
        log.info("图片完整路径: {}", path.toAbsolutePath());
        
        // 检查文件是否存在
        File file = path.toFile();
        if (!file.exists()) {
            log.error("图片不存在: {}", path.toAbsolutePath());
            return ResponseEntity.notFound().build();
        }
        
        // 获取文件的媒体类型
        String contentType;
        try {
            contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }
        
        log.info("图片媒体类型: {}", contentType);
        
        // 返回文件资源
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}
