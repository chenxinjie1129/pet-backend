package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件控制器
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @PostConstruct
    public void init() {
        log.info("FileController初始化");
    }

    @Autowired
    private FileService fileService;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ApiResponse<Map<String, String>> uploadFile(
            @ApiParam("文件") @RequestParam("file") MultipartFile file,
            @ApiParam("目录") @RequestParam(value = "directory", defaultValue = "common") String directory
    ) {
        if (file.isEmpty()) {
            return ApiResponse.error("上传文件不能为空");
        }

        // 检查文件大小，限制为10MB
        if (file.getSize() > 10 * 1024 * 1024) {
            return ApiResponse.error("上传文件大小不能超过10MB");
        }

        // 检查文件类型
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            if (!isAllowedFileType(suffix)) {
                return ApiResponse.error("不支持的文件类型");
            }
        }

        try {
            log.info("开始上传文件: {}, 目录: {}", originalFilename, directory);

            // 上传文件
            String fileUrl = fileService.uploadFile(file, directory);
            log.info("文件上传成功，URL: {}", fileUrl);

            // 返回文件URL
            Map<String, String> result = new HashMap<>();
            result.put("url", fileUrl);
            result.put("name", originalFilename);

            return ApiResponse.successWithMsg("上传成功", result);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return ApiResponse.error("文件上传失败: " + e.getMessage());
        }
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteFile(@ApiParam("文件URL") @RequestParam String fileUrl) {
        boolean result = fileService.deleteFile(fileUrl);

        if (result) {
            return ApiResponse.successWithMsg("删除成功", null);
        } else {
            return ApiResponse.error("删除失败");
        }
    }

    /**
     * 检查文件类型是否允许
     * @param suffix 文件后缀
     * @return 是否允许
     */
    private boolean isAllowedFileType(String suffix) {
        // 允许的文件类型
        String[] allowedTypes = {"jpg", "jpeg", "png", "gif", "bmp", "webp", "mp4", "mov", "pdf", "doc", "docx", "xls", "xlsx", "txt"};

        for (String type : allowedTypes) {
            if (type.equalsIgnoreCase(suffix)) {
                return true;
            }
        }

        return false;
    }
}
