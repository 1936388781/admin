package com.example.homeservice.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    
    PENDING(0, "待派单"),
    ASSIGNED(1, "已派单"),
    ACCEPTED(2, "已接单"),
    IN_PROGRESS(3, "进行中"),
    PENDING_REVIEW(4, "待评价"),
    COMPLETED(5, "已完成"),
    CANCELLED(6, "已取消"),
    REJECTED(7, "已拒绝");
    
    private final Integer code;
    private final String desc;
    
    OrderStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static OrderStatus getByCode(Integer code) {
        for (OrderStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
