package com.example.homeservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    
    private Long id;
    
    private String username;
    
    private String phone;
    
    private String nickname;
    
    private String avatar;
    
    private Integer role;
    
    private Integer status;
    
    private LocalDateTime createTime;
}
