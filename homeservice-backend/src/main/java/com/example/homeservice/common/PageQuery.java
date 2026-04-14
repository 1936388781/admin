package com.example.homeservice.common;

import lombok.Data;

@Data
public class PageQuery {
    
    private Integer current = 1;
    
    private Integer size = 10;
    
    private String keyword;
}
