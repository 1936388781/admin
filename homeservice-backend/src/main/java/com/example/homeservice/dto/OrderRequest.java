package com.example.homeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderRequest {
    
    @NotNull(message = "服务类目不能为空")
    private Long categoryId;
    
    @NotNull(message = "预约时间不能为空")
    private LocalDateTime appointmentTime;
    
    @NotNull(message = "联系人姓名不能为空")
    private String contactName;
    
    @NotNull(message = "联系人电话不能为空")
    private String contactPhone;
    
    @NotNull(message = "服务地址不能为空")
    private String address;
    
    private String remark;
    
    private BigDecimal totalPrice;
}
