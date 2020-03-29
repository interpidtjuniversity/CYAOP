package com.CY.AOP.advice;

import com.CY.AOP.joinpoint.CYMethodJoinPoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Data
public abstract class CYAbstractAdvice implements CYAdvice {
    private Method method;
    private Class<?> aspectClass;
    private ApplicationContext applicationContext;

    /**
     * @param method 切面类方法
     * @param aspectClass 切面类
     * @param applicationContext spring运行时上下文
     * @Author CY
     * */
    public CYAbstractAdvice(Method method,Class<?> aspectClass,ApplicationContext applicationContext) {
        this.method = method;
        this.aspectClass = aspectClass;
        this.applicationContext =applicationContext;
    }

    /**
     * 调用advisor中的advice中的具体方法
     * @param method 通知中的方法
     * @param args 调用参数
     * @param targetMethod 目标方法
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invokeAdvisorMethod(Method method,Object[] args,Method targetMethod) throws InvocationTargetException, IllegalAccessException {

        CYMethodJoinPoint methodJoinPoint = new CYMethodJoinPoint();
        methodJoinPoint.setArgs(args);
        methodJoinPoint.setTargetMethod(targetMethod.getName());
        Object aspectObj =  applicationContext.getBean(aspectClass);
        method.invoke(aspectObj,methodJoinPoint);
    }



}
