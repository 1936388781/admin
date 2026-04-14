package com.example.homeservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("worker")
public class Worker {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private String realName;
    
    private Integer gender;
    
    private Integer age;
    
    private String phone;
    
    private String idCard;
    
    private String skills;
    
    private Integer experience;
    
    private Integer level;
    
    private BigDecimal rating;
    
    private Integer workStatus;
    
    private String introduction;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
