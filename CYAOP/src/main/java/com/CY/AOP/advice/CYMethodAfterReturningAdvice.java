package com.CY.AOP.advice;

import com.CY.AOP.enumaration.CYAdviceExecuteOrderEnum;
import com.CY.AOP.joinpoint.invocation.CYMethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

public class CYMethodAfterReturningAdvice extends CYAbstractAdvice implements Ordered {

    public CYMethodAfterReturningAdvice(Method method, Class<?> aspectClass, ApplicationContext applicationContext) {
        super(method,aspectClass,applicationContext);
    }

    /**
     * 实现该方法，返回
     *
     * @param returnValue 调用结果
     * @param method aspectClass 中的方法
     * @param args 调用目标方法的参数
     * @param targetMethod 目标方法  仅仅为了打印日志而传入
     */
    void afterReturning(Object returnValue, Method method, Object[] args ,Method targetMethod) throws Throwable{
        invokeAdvisorMethod(method,args,targetMethod);
    }


    /**
     * 调用下一个拦截器
     * @param invocation  方法调用对象
     * @return
     * @throws Throwable
     */
    public Object invoke(CYMethodInvocation invocation) throws Throwable {
        Object retVal =  invocation.proceed();                       //这里抛出异常后下面不会执行
        afterReturning(retVal,this.getMethod(),invocation.getArguments(),invocation.getMethod());
        return retVal;
    }

    /**
     * 排序接口
     * @return 1
     */
    public int getOrder() {
        return CYAdviceExecuteOrderEnum.RETURING_ADVICE.getExeOrder();
    }
}
