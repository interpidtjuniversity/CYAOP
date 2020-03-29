package com.CY.AOP.advice;

import com.CY.AOP.enumaration.CYAdviceExecuteOrderEnum;
import com.CY.AOP.joinpoint.invocation.CYMethodInvocation;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class CYMethodBeforeAdvice extends CYAbstractAdvice{

    public CYMethodBeforeAdvice(Method method, Class<?> aspectClass, ApplicationContext applicationContext) {
        super(method, aspectClass, applicationContext);
    }

    /**
     *
     * @param method aspectClass 中的方法
     * @param args 调用目标方法的参数
     * @param targetMethod 目标方法  仅仅为了打印日志而传入
     */
    public void before(Method method, Object[] args, Method targetMethod) throws Throwable{
        invokeAdvisorMethod(method,args,targetMethod);
    }

    public Object invoke(CYMethodInvocation invocation) throws Throwable {
        before(this.getMethod(),invocation.getArguments(),invocation.getMethod());                         //调用前置通知
        return invocation.proceed();                                                                       //放行
    }

    /**
     * 排序接口
     * @return 3
     */
    public int getOrder() {
        return CYAdviceExecuteOrderEnum.BEFORE_ADVICE.getExeOrder();
    }
}
