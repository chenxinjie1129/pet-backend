package com.petshome.api.controller;

import com.petshome.api.common.ApiResponse;
import com.petshome.api.model.entity.Pet;
import com.petshome.api.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 宠物控制器
 */
@Api(tags = "宠物管理")
@RestController
@RequestMapping("/pet")
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;

    @ApiOperation("添加宠物")
    @PostMapping
    public ApiResponse<Pet> addPet(@RequestBody Pet pet) {
        // 验证必要参数
        if (pet.getName() == null || pet.getName().isEmpty()) {
            return ApiResponse.error("宠物名称不能为空");
        }
        if (pet.getType() == null) {
            return ApiResponse.error("宠物类型不能为空");
        }
        if (pet.getUserId() == null) {
            return ApiResponse.error("用户ID不能为空");
        }

        // 添加宠物
        boolean result = petService.addPet(pet);

        if (result) {
            return ApiResponse.successWithMsg("添加成功", pet);
        } else {
            return ApiResponse.error("添加失败");
        }
    }

    @ApiOperation("获取宠物信息")
    @GetMapping("/{id}")
    public ApiResponse<Pet> getPetInfo(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);

        if (pet != null) {
            return ApiResponse.success(pet);
        } else {
            return ApiResponse.error("宠物不存在");
        }
    }

    @ApiOperation("获取用户的宠物列表")
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Pet>> getPetsByUserId(@PathVariable Long userId) {
        List<Pet> petList = petService.getPetsByUserId(userId);
        return ApiResponse.success(petList);
    }

    @ApiOperation("更新宠物信息")
    @PutMapping("/{id}")
    public ApiResponse<Pet> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        pet.setId(id);

        // 验证必要参数
        if (pet.getUserId() == null) {
            return ApiResponse.error("用户ID不能为空");
        }

        boolean result = petService.updatePet(pet);

        if (result) {
            Pet updatedPet = petService.getPetById(id);
            return ApiResponse.successWithMsg("更新成功", updatedPet);
        } else {
            return ApiResponse.error("更新失败，宠物不存在或无权限");
        }
    }

    @ApiOperation("删除宠物")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePet(
            @PathVariable Long id,
            @ApiParam("用户ID") @RequestParam Long userId
    ) {
        boolean result = petService.deletePet(id, userId);

        if (result) {
            return ApiResponse.successWithMsg("删除成功", null);
        } else {
            return ApiResponse.error("删除失败，宠物不存在或无权限");
        }
    }

    @ApiOperation("批量删除宠物")
    @DeleteMapping("/batch")
    public ApiResponse<Void> batchDeletePet(
            @RequestBody List<Long> ids,
            @ApiParam("用户ID") @RequestParam Long userId
    ) {
        boolean result = petService.batchDeletePet(ids, userId);

        if (result) {
            return ApiResponse.successWithMsg("批量删除成功", null);
        } else {
            return ApiResponse.error("批量删除失败");
        }
    }

    @ApiOperation("更新宠物状态")
    @PutMapping("/{id}/status/{status}")
    public ApiResponse<Void> updatePetStatus(
            @PathVariable Long id,
            @PathVariable Integer status,
            @ApiParam("用户ID") @RequestParam Long userId
    ) {
        boolean result = petService.updatePetStatus(id, status, userId);

        if (result) {
            return ApiResponse.successWithMsg("状态更新成功", null);
        } else {
            return ApiResponse.error("状态更新失败，宠物不存在或无权限");
        }
    }

    @ApiOperation("获取所有宠物列表")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Pet>> getPetList() {
        List<Pet> petList = petService.getPetList();
        return ApiResponse.success(petList);
    }
}
