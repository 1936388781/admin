package com.example.homeservice.controller;

import com.example.homeservice.common.Result;
import com.example.homeservice.dto.LoginRequest;
import com.example.homeservice.dto.RegisterRequest;
import com.example.homeservice.entity.User;
import com.example.homeservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "认证管理", description = "用户注册登录相关接口")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;
    
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return Result.success("注册成功");
    }
    
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        
        return Result.success("登录成功", data);
    }
    
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<?> getCurrentUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            return Result.error(401, "未登录");
        }
        return Result.success(userService.getCurrentUser(user.getId()));
    }
    
    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public Result<Void> updatePassword(@AuthenticationPrincipal User user,
                                       @RequestParam String oldPassword,
                                       @RequestParam String newPassword) {
        userService.updatePassword(user.getId(), oldPassword, newPassword);
        return Result.success("密码修改成功");
    }
}
