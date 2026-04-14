package com.example.homeservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.common.Result;
import com.example.homeservice.dto.OrderRequest;
import com.example.homeservice.entity.User;
import com.example.homeservice.service.OrderService;
import com.example.homeservice.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理", description = "订单相关接口")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @Operation(summary = "创建订单")
    @PostMapping
    public Result<OrderVO> createOrder(@AuthenticationPrincipal User user, @Valid @RequestBody OrderRequest request) {
        return Result.success(orderService.createOrder(user.getId(), request));
    }
    
    @Operation(summary = "获取我的订单列表")
    @GetMapping
    public Result<Page<OrderVO>> getMyOrders(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        return Result.success(orderService.getUserOrderPage(user.getId(), current, size, status));
    }
    
    @Operation(summary = "获取订单详情")
    @GetMapping("/{id}")
    public Result<OrderVO> getOrderDetail(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return Result.success(orderService.getOrderDetail(user.getId(), id));
    }
    
    @Operation(summary = "取消订单")
    @PutMapping("/{id}/cancel")
    public Result<Void> cancelOrder(@AuthenticationPrincipal User user, 
                                    @PathVariable Long id, 
                                    @RequestParam(required = false) String reason) {
        orderService.cancelOrder(user.getId(), id, reason);
        return Result.success();
    }
    
    @Operation(summary = "确认完成")
    @PutMapping("/{id}/confirm")
    public Result<Void> confirmOrder(@AuthenticationPrincipal User user, @PathVariable Long id) {
        orderService.confirmOrder(user.getId(), id);
        return Result.success();
    }
}
