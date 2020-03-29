package com.CY.AOP.advice;

import com.CY.AOP.enumaration.CYAdviceExecuteOrderEnum;
import com.CY.AOP.joinpoint.invocation.CYMethodInvocation;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class CYMethodAfterAdvice extends CYAbstractAdvice{
    public CYMethodAfterAdvice(Method method, Class<?> aspectClass, ApplicationContext applicationContext) {
        super(method, aspectClass, applicationContext);
    }


    /**
     *
     * @param method aspectClass 中的方法
     * @param args 调用目标方法的参数
     * @param targetMethod 目标方法  仅仅为了打印日志而传入
     */
    public  void after(Method method, Object[] args,Method targetMethod) throws Throwable{
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
            return invocation.proceed();                               //放行
        } finally {
            //后置通知被finally包裹 所以总是会执行                         调用后置通知
            after(this.getMethod(),invocation.getArguments(),invocation.getMethod());
        }
    }

    /**
     * 用于实现排序接口
     * @return 2
     */
    public int getOrder() {
        return CYAdviceExecuteOrderEnum.AFTER_ADVICE.getExeOrder();
    }

}
