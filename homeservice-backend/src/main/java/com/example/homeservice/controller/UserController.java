package com.example.homeservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.common.Result;
import com.example.homeservice.entity.User;
import com.example.homeservice.service.UserService;
import com.example.homeservice.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理", description = "用户信息相关接口")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<UserVO> getUserInfo(@AuthenticationPrincipal User user) {
        return Result.success(userService.getCurrentUser(user.getId()));
    }
    
    @Operation(summary = "修改个人信息")
    @PutMapping("/info")
    public Result<UserVO> updateUserInfo(@AuthenticationPrincipal User user, @RequestBody UserVO userVO) {
        return Result.success(userService.updateUserInfo(user.getId(), userVO));
    }
    
    @Operation(summary = "获取用户列表（管理员）")
    @GetMapping("/list")
    public Result<Page<UserVO>> getUserList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        return Result.success(userService.getUserPage(current, size, keyword));
    }
    
    @Operation(summary = "启用/禁用用户（管理员）")
    @PutMapping("/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateUserStatus(id, status);
        return Result.success();
    }
}
