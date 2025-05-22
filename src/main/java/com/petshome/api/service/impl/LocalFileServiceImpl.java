package com.petshome.api.service.impl;

import com.petshome.api.exception.BusinessException;
import com.petshome.api.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.annotation.PostConstruct;

/**
 * 本地文件服务实现类
 */
@Service
@Primary
@Slf4j
public class LocalFileServiceImpl implements FileService {

    @Value("${petshome.upload.local.upload-dir:uploads}")
    private String uploadDir;

    @Value("${petshome.upload.local.url-prefix:http://localhost:8080/api/uploads/}")
    private String urlPrefix;

    // 在初始化后打印URL前缀，便于调试
    @PostConstruct
    public void init() {
        log.info("本地文件存储URL前缀: {}", urlPrefix);
        log.info("本地文件存储目录: {}", uploadDir);
    }

    @Override
    public String uploadFile(MultipartFile file, String directory) {
        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件后缀
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 生成新的文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

            // 按日期生成目录
            String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

            // 构建文件路径
            String filePath = directory + "/" + datePath + "/" + fileName;

            // 确保目录存在
            Path uploadPath = Paths.get(uploadDir, directory, datePath);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件
            Path targetPath = Paths.get(uploadDir, filePath);
            Files.copy(file.getInputStream(), targetPath);

            // 返回文件访问路径
            return urlPrefix + filePath;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        // 如果文件URL为空，直接返回成功
        if (fileUrl == null || fileUrl.isEmpty()) {
            return true;
        }

        try {
            // 从URL中提取文件路径
            String filePath = fileUrl.replace(urlPrefix, "");

            // 构建文件完整路径
            Path targetPath = Paths.get(uploadDir, filePath);

            // 删除文件
            return Files.deleteIfExists(targetPath);
        } catch (Exception e) {
            log.error("文件删除失败", e);
            return false;
        }
    }
}
