package com.example.homeservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewVO {
    
    private Long id;
    
    private Long orderId;
    
    private Long userId;
    
    private String userName;
    
    private Long workerId;
    
    private String workerName;
    
    private Integer rating;
    
    private String content;
    
    private String images;
    
    private LocalDateTime createTime;
}
