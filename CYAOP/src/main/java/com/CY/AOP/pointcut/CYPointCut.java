package com.CY.AOP.pointcut;

import java.lang.reflect.Method;

//切点 织入点
public interface CYPointCut {
    /**
     * 根据当前的正在创建的类来判断该类是否满足切点表达式
     * @param targetClass 正在创建的类
     * @return true|false
     */
    boolean matchClass(Class<?> targetClass);

    /**
     * 匹配方法
     * @param method 当前正在执行的类上的方法
     * @param targetClass 正在调用的类
     * @return true|false
     */
    boolean matchMethod(Method method, Class<?> targetClass);
}
