package com.example.homeservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.homeservice.common.BusinessException;
import com.example.homeservice.dto.OrderRequest;
import com.example.homeservice.entity.Order;
import com.example.homeservice.entity.ServiceCategory;
import com.example.homeservice.entity.User;
import com.example.homeservice.entity.Worker;
import com.example.homeservice.enums.OrderStatus;
import com.example.homeservice.mapper.OrderMapper;
import com.example.homeservice.mapper.ServiceCategoryMapper;
import com.example.homeservice.mapper.UserMapper;
import com.example.homeservice.mapper.WorkerMapper;
import com.example.homeservice.service.OrderService;
import com.example.homeservice.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final WorkerMapper workerMapper;
    private final ServiceCategoryMapper serviceCategoryMapper;
    
    public OrderServiceImpl(OrderMapper orderMapper, UserMapper userMapper, 
                           WorkerMapper workerMapper, ServiceCategoryMapper serviceCategoryMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.workerMapper = workerMapper;
        this.serviceCategoryMapper = serviceCategoryMapper;
    }
    
    @Override
    public OrderVO createOrder(Long userId, OrderRequest request) {
        ServiceCategory category = serviceCategoryMapper.selectById(request.getCategoryId());
        if (category == null) {
            throw new BusinessException("服务类目不存在");
        }
        
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setCategoryId(request.getCategoryId());
        order.setCategoryName(category.getName());
        order.setAppointmentTime(request.getAppointmentTime());
        order.setContactName(request.getContactName());
        order.setContactPhone(request.getContactPhone());
        order.setAddress(request.getAddress());
        order.setRemark(request.getRemark());
        order.setTotalPrice(request.getTotalPrice() != null ? request.getTotalPrice() : category.getPrice());
        order.setStatus(OrderStatus.PENDING.getCode());
        
        orderMapper.insert(order);
        
        return convertToVO(order);
    }
    
    @Override
    public Page<OrderVO> getUserOrderPage(Long userId, Integer current, Integer size, Integer status) {
        Page<Order> page = new Page<>(current, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        
        Page<Order> orderPage = orderMapper.selectPage(page, wrapper);
        
        return convertPageToVO(orderPage);
    }
    
    @Override
    public OrderVO getOrderDetail(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权查看此订单");
        }
        
        return convertToVO(order);
    }
    
    @Override
    public void cancelOrder(Long userId, Long orderId, String reason) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作此订单");
        }
        
        if (order.getStatus() != OrderStatus.PENDING.getCode() && 
            order.getStatus() != OrderStatus.ASSIGNED.getCode()) {
            throw new BusinessException("当前订单状态不允许取消");
        }
        
        order.setStatus(OrderStatus.CANCELLED.getCode());
        order.setCancelReason(reason);
        orderMapper.updateById(order);
    }
    
    @Override
    public void confirmOrder(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作此订单");
        }
        
        if (order.getStatus() != OrderStatus.IN_PROGRESS.getCode()) {
            throw new BusinessException("当前订单状态不允许确认");
        }
        
        order.setStatus(OrderStatus.PENDING_REVIEW.getCode());
        order.setFinishTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }
    
    @Override
    public Page<OrderVO> getAdminOrderPage(Integer current, Integer size, Integer status, String keyword) {
        Page<Order> page = new Page<>(current, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Order::getOrderNo, keyword)
                   .or()
                   .like(Order::getContactName, keyword)
                   .or()
                   .like(Order::getContactPhone, keyword);
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        
        Page<Order> orderPage = orderMapper.selectPage(page, wrapper);
        
        return convertPageToVO(orderPage);
    }
    
    @Override
    public void assignOrder(Long orderId, Long workerId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (order.getStatus() != OrderStatus.PENDING.getCode()) {
            throw new BusinessException("当前订单状态不允许派单");
        }
        
        Worker worker = workerMapper.selectById(workerId);
        if (worker == null || worker.getStatus() != 1) {
            throw new BusinessException("服务人员不存在或未通过审核");
        }
        
        order.setWorkerId(workerId);
        order.setStatus(OrderStatus.ASSIGNED.getCode());
        orderMapper.updateById(order);
    }
    
    @Override
    public void handleOrderException(Long orderId, String reason) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        order.setStatus(OrderStatus.CANCELLED.getCode());
        order.setCancelReason(reason);
        orderMapper.updateById(order);
    }
    
    @Override
    public Page<OrderVO> getWorkerOrderPage(Long workerId, Integer current, Integer size, Integer status) {
        Page<Order> page = new Page<>(current, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getWorkerId, workerId);
        
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        
        wrapper.orderByDesc(Order::getCreateTime);
        
        Page<Order> orderPage = orderMapper.selectPage(page, wrapper);
        
        return convertPageToVO(orderPage);
    }
    
    @Override
    public void acceptOrder(Long workerId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!workerId.equals(order.getWorkerId())) {
            throw new BusinessException("无权操作此订单");
        }
        
        if (order.getStatus() != OrderStatus.ASSIGNED.getCode()) {
            throw new BusinessException("当前订单状态不允许接单");
        }
        
        order.setStatus(OrderStatus.ACCEPTED.getCode());
        orderMapper.updateById(order);
    }
    
    @Override
    public void rejectOrder(Long workerId, Long orderId, String reason) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!workerId.equals(order.getWorkerId())) {
            throw new BusinessException("无权操作此订单");
        }
        
        if (order.getStatus() != OrderStatus.ASSIGNED.getCode()) {
            throw new BusinessException("当前订单状态不允许拒单");
        }
        
        order.setStatus(OrderStatus.REJECTED.getCode());
        order.setCancelReason(reason);
        order.setWorkerId(null);
        orderMapper.updateById(order);
    }
    
    @Override
    public void finishOrder(Long workerId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!workerId.equals(order.getWorkerId())) {
            throw new BusinessException("无权操作此订单");
        }
        
        if (order.getStatus() != OrderStatus.ACCEPTED.getCode() && 
            order.getStatus() != OrderStatus.IN_PROGRESS.getCode()) {
            throw new BusinessException("当前订单状态不允许完成");
        }
        
        order.setStatus(OrderStatus.IN_PROGRESS.getCode());
        orderMapper.updateById(order);
    }
    
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "ORD" + timestamp + random;
    }
    
    private OrderVO convertToVO(Order order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        
        OrderStatus status = OrderStatus.getByCode(order.getStatus());
        if (status != null) {
            vo.setStatusDesc(status.getDesc());
        }
        
        if (order.getUserId() != null) {
            User user = userMapper.selectById(order.getUserId());
            if (user != null) {
                vo.setUserName(user.getNickname());
            }
        }
        
        if (order.getWorkerId() != null) {
            Worker worker = workerMapper.selectById(order.getWorkerId());
            if (worker != null) {
                vo.setWorkerName(worker.getRealName());
            }
        }
        
        return vo;
    }
    
    private Page<OrderVO> convertPageToVO(Page<Order> orderPage) {
        Page<OrderVO> voPage = new Page<>();
        voPage.setCurrent(orderPage.getCurrent());
        voPage.setSize(orderPage.getSize());
        voPage.setTotal(orderPage.getTotal());
        voPage.setPages(orderPage.getPages());
        
        List<OrderVO> voList = orderPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        voPage.setRecords(voList);
        return voPage;
    }
}
