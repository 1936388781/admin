package com.example.homeservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.common.Result;
import com.example.homeservice.service.OrderService;
import com.example.homeservice.service.ServiceCategoryService;
import com.example.homeservice.service.UserService;
import com.example.homeservice.vo.OrderVO;
import com.example.homeservice.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "管理后台", description = "管理员相关接口")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    private final UserService userService;
    private final OrderService orderService;
    private final ServiceCategoryService serviceCategoryService;
    
    public AdminController(UserService userService, OrderService orderService, ServiceCategoryService serviceCategoryService) {
        this.userService = userService;
        this.orderService = orderService;
        this.serviceCategoryService = serviceCategoryService;
    }
    
    @Operation(summary = "获取用户列表")
    @GetMapping("/users")
    public Result<Page<UserVO>> getUserList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        return Result.success(userService.getUserPage(current, size, keyword));
    }
    
    @Operation(summary = "启用/禁用用户")
    @PutMapping("/users/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateUserStatus(id, status);
        return Result.success();
    }
    
    @Operation(summary = "获取订单列表")
    @GetMapping("/orders")
    public Result<Page<OrderVO>> getOrderList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword) {
        return Result.success(orderService.getAdminOrderPage(current, size, status, keyword));
    }
    
    @Operation(summary = "订单派单")
    @PutMapping("/orders/{id}/assign")
    public Result<Void> assignOrder(@PathVariable Long id, @RequestParam Long workerId) {
        orderService.assignOrder(id, workerId);
        return Result.success();
    }
    
    @Operation(summary = "处理订单异常")
    @PutMapping("/orders/{id}/exception")
    public Result<Void> handleOrderException(@PathVariable Long id, @RequestParam String reason) {
        orderService.handleOrderException(id, reason);
        return Result.success();
    }
    
    @Operation(summary = "获取数据统计")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", 0);
        stats.put("totalOrders", 0);
        stats.put("totalRevenue", 0);
        return Result.success(stats);
    }
}
