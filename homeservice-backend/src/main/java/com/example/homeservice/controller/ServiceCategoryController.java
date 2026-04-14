package com.example.homeservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.common.Result;
import com.example.homeservice.entity.ServiceCategory;
import com.example.homeservice.service.ServiceCategoryService;
import com.example.homeservice.vo.ServiceCategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "服务类目管理", description = "服务类目相关接口")
@RestController
@RequestMapping("/api/categories")
public class ServiceCategoryController {
    
    private final ServiceCategoryService serviceCategoryService;
    
    public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
        this.serviceCategoryService = serviceCategoryService;
    }
    
    @Operation(summary = "获取所有服务类目")
    @GetMapping
    public Result<List<ServiceCategoryVO>> getAllCategories() {
        return Result.success(serviceCategoryService.getAllCategories());
    }
    
    @Operation(summary = "获取服务类目详情")
    @GetMapping("/{id}")
    public Result<ServiceCategoryVO> getCategoryById(@PathVariable Long id) {
        return Result.success(serviceCategoryService.getCategoryById(id));
    }
    
    @Operation(summary = "获取服务类目列表（管理员）")
    @GetMapping("/page")
    public Result<Page<ServiceCategoryVO>> getCategoryPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        return Result.success(serviceCategoryService.getCategoryPage(current, size, keyword));
    }
    
    @Operation(summary = "新增服务类目（管理员）")
    @PostMapping
    public Result<Void> addCategory(@RequestBody ServiceCategory category) {
        serviceCategoryService.addCategory(category);
        return Result.success();
    }
    
    @Operation(summary = "修改服务类目（管理员）")
    @PutMapping("/{id}")
    public Result<Void> updateCategory(@PathVariable Long id, @RequestBody ServiceCategory category) {
        category.setId(id);
        serviceCategoryService.updateCategory(category);
        return Result.success();
    }
    
    @Operation(summary = "删除服务类目（管理员）")
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        serviceCategoryService.deleteCategory(id);
        return Result.success();
    }
}
