package com.example.homeservice.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderVO {
    
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    private String userName;
    
    private Long workerId;
    
    private String workerName;
    
    private Long categoryId;
    
    private String categoryName;
    
    private LocalDateTime appointmentTime;
    
    private String contactName;
    
    private String contactPhone;
    
    private String address;
    
    private String remark;
    
    private BigDecimal totalPrice;
    
    private Integer status;
    
    private String statusDesc;
    
    private String cancelReason;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private LocalDateTime finishTime;
}
