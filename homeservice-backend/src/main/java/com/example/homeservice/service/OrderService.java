package com.example.homeservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.dto.OrderRequest;
import com.example.homeservice.entity.Order;
import com.example.homeservice.vo.OrderVO;

public interface OrderService {
    
    OrderVO createOrder(Long userId, OrderRequest request);
    
    Page<OrderVO> getUserOrderPage(Long userId, Integer current, Integer size, Integer status);
    
    OrderVO getOrderDetail(Long userId, Long orderId);
    
    void cancelOrder(Long userId, Long orderId, String reason);
    
    void confirmOrder(Long userId, Long orderId);
    
    Page<OrderVO> getAdminOrderPage(Integer current, Integer size, Integer status, String keyword);
    
    void assignOrder(Long orderId, Long workerId);
    
    void handleOrderException(Long orderId, String reason);
    
    Page<OrderVO> getWorkerOrderPage(Long workerId, Integer current, Integer size, Integer status);
    
    void acceptOrder(Long workerId, Long orderId);
    
    void rejectOrder(Long workerId, Long orderId, String reason);
    
    void finishOrder(Long workerId, Long orderId);
}
