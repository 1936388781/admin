package com.example.homeservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.entity.ServiceCategory;
import com.example.homeservice.vo.ServiceCategoryVO;

import java.util.List;

public interface ServiceCategoryService {
    
    List<ServiceCategoryVO> getAllCategories();
    
    ServiceCategoryVO getCategoryById(Long id);
    
    Page<ServiceCategoryVO> getCategoryPage(Integer current, Integer size, String keyword);
    
    void addCategory(ServiceCategory category);
    
    void updateCategory(ServiceCategory category);
    
    void deleteCategory(Long id);
}
