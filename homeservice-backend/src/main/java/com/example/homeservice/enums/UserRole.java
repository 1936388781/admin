package com.example.homeservice.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    
    USER(0, "普通用户"),
    WORKER(1, "服务人员"),
    ADMIN(2, "管理员");
    
    private final Integer code;
    private final String desc;
    
    UserRole(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public static UserRole getByCode(Integer code) {
        for (UserRole role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }
}
