package com.example.homeservice.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ServiceCategoryVO {
    
    private Long id;
    
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    private String icon;
    
    private Integer sortOrder;
    
    private Integer status;
    
    private LocalDateTime createTime;
}
