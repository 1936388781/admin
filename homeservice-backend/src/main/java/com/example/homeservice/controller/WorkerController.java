package com.example.homeservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.common.Result;
import com.example.homeservice.entity.User;
import com.example.homeservice.service.OrderService;
import com.example.homeservice.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "服务人员管理", description = "服务人员相关接口")
@RestController
@RequestMapping("/api/workers")
public class WorkerController {
    
    private final OrderService orderService;
    
    public WorkerController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @Operation(summary = "获取我的订单列表")
    @GetMapping("/orders")
    public Result<Page<OrderVO>> getMyOrders(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        return Result.success(orderService.getWorkerOrderPage(user.getId(), current, size, status));
    }
    
    @Operation(summary = "接单")
    @PutMapping("/orders/{id}/accept")
    public Result<Void> acceptOrder(@AuthenticationPrincipal User user, @PathVariable Long id) {
        orderService.acceptOrder(user.getId(), id);
        return Result.success();
    }
    
    @Operation(summary = "拒单")
    @PutMapping("/orders/{id}/reject")
    public Result<Void> rejectOrder(@AuthenticationPrincipal User user, 
                                    @PathVariable Long id, 
                                    @RequestParam(required = false) String reason) {
        orderService.rejectOrder(user.getId(), id, reason);
        return Result.success();
    }
    
    @Operation(summary = "开始服务")
    @PutMapping("/orders/{id}/start")
    public Result<Void> startOrder(@AuthenticationPrincipal User user, @PathVariable Long id) {
        orderService.finishOrder(user.getId(), id);
        return Result.success();
    }
}
