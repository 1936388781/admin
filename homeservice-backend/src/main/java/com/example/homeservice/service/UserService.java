package com.example.homeservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.dto.LoginRequest;
import com.example.homeservice.dto.RegisterRequest;
import com.example.homeservice.entity.User;
import com.example.homeservice.vo.UserVO;

public interface UserService {
    
    void register(RegisterRequest request);
    
    String login(LoginRequest request);
    
    UserVO getCurrentUser(Long userId);
    
    UserVO updateUserInfo(Long userId, UserVO userVO);
    
    void updatePassword(Long userId, String oldPassword, String newPassword);
    
    Page<UserVO> getUserPage(Integer current, Integer size, String keyword);
    
    void updateUserStatus(Long userId, Integer status);
}
