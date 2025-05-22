package com.petshome.api.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 */
public interface FileService {
    
    /**
     * 上传文件到OSS
     * @param file 文件
     * @param directory 目录
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String directory);
    
    /**
     * 删除OSS文件
     * @param fileUrl 文件URL
     * @return 删除结果
     */
    boolean deleteFile(String fileUrl);
}
