package com.example.homeservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    private Long workerId;
    
    private Long categoryId;
    
    private String categoryName;
    
    private LocalDateTime appointmentTime;
    
    private String contactName;
    
    private String contactPhone;
    
    private String address;
    
    private String remark;
    
    private BigDecimal totalPrice;
    
    private Integer status;
    
    private String cancelReason;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    private LocalDateTime finishTime;
}
