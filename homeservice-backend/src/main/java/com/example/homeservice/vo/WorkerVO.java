package com.example.homeservice.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WorkerVO {
    
    private Long id;
    
    private Long userId;
    
    private String realName;
    
    private Integer gender;
    
    private Integer age;
    
    private String phone;
    
    private String skills;
    
    private Integer experience;
    
    private Integer level;
    
    private BigDecimal rating;
    
    private Integer workStatus;
    
    private String workStatusDesc;
    
    private String introduction;
    
    private Integer status;
    
    private String avatar;
    
    private LocalDateTime createTime;
}
