package com.CY.AOP.joinpoint;

public interface CYJoinPoint {

    /**
     * 获取下一个方法拦截器
     * @return 下一个拦截器 CYAdvice接口类型的
     * @throws Throwable
     */
    Object proceed() throws Throwable;
}
