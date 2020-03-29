package com.CY.AOP.joinpoint.interceptor;

import com.CY.AOP.joinpoint.invocation.CYMethodInvocation;

public interface CYMethodInterceptor {
    /**
     * 拦截方法调用 并且调用
     * @param invocation  方法调用对象
     * @return Object
     * @throws Throwable 抛出的异常
     */
    Object invoke(CYMethodInvocation invocation) throws Throwable;
}
