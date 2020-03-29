package com.CY.AOP.joinpoint;

import lombok.Data;

@Data
public class CYMethodJoinPoint {
    private Object target;

    private Object[] args;

    private String targetMethod;
}
