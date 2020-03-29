package com.CY.AOP.advice;

import com.CY.AOP.enumaration.CYAdviceExecuteOrderEnum;
import com.CY.AOP.joinpoint.invocation.CYMethodInvocation;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class CYMethodThrowingAdvice extends CYAbstractAdvice{
    public CYMethodThrowingAdvice(Method method, Class<?> aspectClass, ApplicationContext applicationContext) {
        super(method, aspectClass, applicationContext);
    }

    /**
     *
     * @param method aspectClass 中的方法
     * @param args 调用目标方法的参数
     * @param targetMethod 目标方法  仅仅为了打印日志而传入
     */
    public  void throwing(Method method, Object[] args, Method targetMethod) throws Throwable{
        invokeAdvisorMethod(method,args,targetMethod);
    }

    /**
     * 调用下一个拦截器
     * @param invocation  方法调用对象
     * @return
     * @throws Throwable
     */
    public Object invoke(CYMethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();                                                    //放行
        } catch (Throwable ex){
            throwing(this.getMethod(),invocation.getArguments(),invocation.getMethod());    //返回异常
            throw ex;
        }
    }

    /**
     * 排序接口
     * @return 0
     */
    public int getOrder() {
        return CYAdviceExecuteOrderEnum.THROWING_ADVICE.getExeOrder();
    }
}
