package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.model.entity.Banner;
import com.petshome.api.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图控制器
 */
@Api(tags = "轮播图管理")
@RestController
@RequestMapping("/banner")
@Slf4j
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ApiOperation("获取轮播图列表")
    @GetMapping("/list")
    public ApiResponse<List<Banner>> getBannerList() {
        List<Banner> bannerList = bannerService.getBannerList();
        return ApiResponse.success(bannerList);
    }

    @ApiOperation("获取启用的轮播图列表")
    @GetMapping("/active")
    public ApiResponse<List<Banner>> getActiveBannerList() {
        List<Banner> bannerList = bannerService.getActiveBannerList();
        return ApiResponse.success(bannerList);
    }

    @ApiOperation("获取轮播图详情")
    @GetMapping("/{id}")
    public ApiResponse<Banner> getBannerDetail(@PathVariable Long id) {
        Banner banner = bannerService.getBannerById(id);

        if (banner != null) {
            return ApiResponse.success(banner);
        } else {
            return ApiResponse.error("轮播图不存在");
        }
    }

    @ApiOperation("添加轮播图")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Banner> addBanner(@RequestBody Banner banner) {
        // 验证必要参数
        if (banner.getTitle() == null || banner.getTitle().isEmpty()) {
            return ApiResponse.error("标题不能为空");
        }
        if (banner.getImageUrl() == null || banner.getImageUrl().isEmpty()) {
            return ApiResponse.error("图片URL不能为空");
        }

        boolean result = bannerService.addBanner(banner);

        if (result) {
            return ApiResponse.successWithMsg("添加成功", banner);
        } else {
            return ApiResponse.error("添加失败");
        }
    }

    @ApiOperation("更新轮播图")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Banner> updateBanner(@PathVariable Long id, @RequestBody Banner banner) {
        banner.setId(id);

        boolean result = bannerService.updateBanner(banner);

        if (result) {
            Banner updatedBanner = bannerService.getBannerById(id);
            return ApiResponse.successWithMsg("更新成功", updatedBanner);
        } else {
            return ApiResponse.error("更新失败，轮播图不存在");
        }
    }

    @ApiOperation("删除轮播图")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteBanner(@PathVariable Long id) {
        boolean result = bannerService.deleteBanner(id);

        if (result) {
            return ApiResponse.successWithMsg("删除成功", null);
        } else {
            return ApiResponse.error("删除失败，轮播图不存在");
        }
    }

    @ApiOperation("批量删除轮播图")
    @DeleteMapping("/batch")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> batchDeleteBanner(@RequestBody List<Long> ids) {
        boolean result = bannerService.batchDeleteBanner(ids);

        if (result) {
            return ApiResponse.successWithMsg("批量删除成功", null);
        } else {
            return ApiResponse.error("批量删除失败");
        }
    }

    @ApiOperation("更新轮播图状态")
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateBannerStatus(@PathVariable Long id, @PathVariable Integer status) {
        boolean result = bannerService.updateBannerStatus(id, status);

        if (result) {
            return ApiResponse.successWithMsg("状态更新成功", null);
        } else {
            return ApiResponse.error("状态更新失败，轮播图不存在");
        }
    }
}
