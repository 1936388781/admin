package com.example.homeservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.common.BusinessException;
import com.example.homeservice.entity.ServiceCategory;
import com.example.homeservice.mapper.ServiceCategoryMapper;
import com.example.homeservice.service.ServiceCategoryService;
import com.example.homeservice.vo.ServiceCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    
    private final ServiceCategoryMapper serviceCategoryMapper;
    
    public ServiceCategoryServiceImpl(ServiceCategoryMapper serviceCategoryMapper) {
        this.serviceCategoryMapper = serviceCategoryMapper;
    }
    
    @Override
    public List<ServiceCategoryVO> getAllCategories() {
        LambdaQueryWrapper<ServiceCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ServiceCategory::getStatus, 1)
               .orderByAsc(ServiceCategory::getSortOrder);
        
        List<ServiceCategory> categories = serviceCategoryMapper.selectList(wrapper);
        
        return categories.stream().map(category -> {
            ServiceCategoryVO vo = new ServiceCategoryVO();
            BeanUtils.copyProperties(category, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    public ServiceCategoryVO getCategoryById(Long id) {
        ServiceCategory category = serviceCategoryMapper.selectById(id);
        if (category == null) {
            throw new BusinessException("服务类目不存在");
        }
        
        ServiceCategoryVO vo = new ServiceCategoryVO();
        BeanUtils.copyProperties(category, vo);
        return vo;
    }
    
    @Override
    public Page<ServiceCategoryVO> getCategoryPage(Integer current, Integer size, String keyword) {
        Page<ServiceCategory> page = new Page<>(current, size);
        LambdaQueryWrapper<ServiceCategory> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(ServiceCategory::getName, keyword);
        }
        
        wrapper.orderByAsc(ServiceCategory::getSortOrder);
        
        Page<ServiceCategory> categoryPage = serviceCategoryMapper.selectPage(page, wrapper);
        
        Page<ServiceCategoryVO> voPage = new Page<>();
        voPage.setCurrent(categoryPage.getCurrent());
        voPage.setSize(categoryPage.getSize());
        voPage.setTotal(categoryPage.getTotal());
        voPage.setPages(categoryPage.getPages());
        
        List<ServiceCategoryVO> voList = categoryPage.getRecords().stream().map(category -> {
            ServiceCategoryVO vo = new ServiceCategoryVO();
            BeanUtils.copyProperties(category, vo);
            return vo;
        }).collect(Collectors.toList());
        
        voPage.setRecords(voList);
        return voPage;
    }
    
    @Override
    public void addCategory(ServiceCategory category) {
        category.setStatus(1);
        serviceCategoryMapper.insert(category);
    }
    
    @Override
    public void updateCategory(ServiceCategory category) {
        ServiceCategory existing = serviceCategoryMapper.selectById(category.getId());
        if (existing == null) {
            throw new BusinessException("服务类目不存在");
        }
        
        serviceCategoryMapper.updateById(category);
    }
    
    @Override
    public void deleteCategory(Long id) {
        serviceCategoryMapper.deleteById(id);
    }
}
