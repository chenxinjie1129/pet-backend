package com.petshome.api.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.petshome.api.exception.BusinessException;
import com.petshome.api.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件服务实现类
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    
    @Value("${petshome.upload.oss.endpoint}")
    private String endpoint;
    
    @Value("${petshome.upload.oss.access-key-id}")
    private String accessKeyId;
    
    @Value("${petshome.upload.oss.access-key-secret}")
    private String accessKeySecret;
    
    @Value("${petshome.upload.oss.bucket-name}")
    private String bucketName;
    
    @Value("${petshome.upload.oss.url-prefix}")
    private String urlPrefix;
    
    @Override
    public String uploadFile(MultipartFile file, String directory) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        
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
            
            // 构建OSS文件路径
            String ossFilePath = directory + "/" + datePath + "/" + fileName;
            
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 设置ContentLength
            metadata.setContentLength(file.getSize());
            // 设置ContentType
            metadata.setContentType(getContentType(suffix));
            
            // 上传文件
            ossClient.putObject(bucketName, ossFilePath, inputStream, metadata);
            
            // 关闭OSSClient
            ossClient.shutdown();
            
            // 返回文件访问路径
            return urlPrefix + ossFilePath;
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
        
        // 从URL中提取文件路径
        String filePath = fileUrl.replace(urlPrefix, "");
        
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        
        try {
            // 删除文件
            ossClient.deleteObject(bucketName, filePath);
            
            // 关闭OSSClient
            ossClient.shutdown();
            
            return true;
        } catch (Exception e) {
            log.error("文件删除失败", e);
            return false;
        }
    }
    
    /**
     * 获取文件的ContentType
     * @param suffix 文件后缀
     * @return ContentType
     */
    private String getContentType(String suffix) {
        if (suffix == null) {
            return "application/octet-stream";
        }
        suffix = suffix.toLowerCase();
        
        switch (suffix) {
            case ".jpg":
            case ".jpeg":
                return "image/jpeg";
            case ".png":
                return "image/png";
            case ".gif":
                return "image/gif";
            case ".bmp":
                return "image/bmp";
            case ".webp":
                return "image/webp";
            case ".mp4":
                return "video/mp4";
            case ".mov":
                return "video/quicktime";
            case ".pdf":
                return "application/pdf";
            case ".doc":
                return "application/msword";
            case ".docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case ".xls":
                return "application/vnd.ms-excel";
            case ".xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case ".txt":
                return "text/plain";
            default:
                return "application/octet-stream";
        }
    }
}
