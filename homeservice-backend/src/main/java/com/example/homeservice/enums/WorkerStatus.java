package com.example.homeservice.enums;

import lombok.Getter;

@Getter
public enum WorkerStatus {
    
    REST(0, "休息"),
    IDLE(1, "空闲"),
    BUSY(2, "忙碌");
    
    private final Integer code;
    private final String desc;
    
    WorkerStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static WorkerStatus getByCode(Integer code) {
        for (WorkerStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
