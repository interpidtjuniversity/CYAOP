package com.CY.AOP.proxy;

import com.CY.AOP.advisor.CYAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

@Slf4j
public class CYJdkDynamicProxy implements CYAOPProxy,InvocationHandler{

    private String beanName;
    /**
     * @target 重要!!!!!!!!!!被代理对象
     * */
    private Object target;
    private List<CYAdvisor> matchAdvisors;

    private ApplicationContext applicationContext;

    /**
     *
     * @param target 被代理对象
     * */
    public CYJdkDynamicProxy(String beanName, Object target, List<CYAdvisor> matchAdvisors, ApplicationContext applicationContext) {
        super();
        this.beanName = beanName;
        this.target = target;
        this.matchAdvisors = matchAdvisors;
        this.applicationContext = applicationContext;
    }

    public Object getProxy() {
        return this.getProxy(target.getClass().getClassLoader());
    }


    public Object getProxy(ClassLoader classLoader) {
        if (log.isDebugEnabled()) {
            log.debug("为" + target + "创建代理。");
        }                                                                          //InvocationHandler
        return Proxy.newProxyInstance(classLoader, target.getClass().getInterfaces(), this);    //目标类的classLoader  interface
    }


    /**
     * @param proxy 代理类
     * @param method 代理类的方法(...业务方法...其中一个)    名字应该和被代理类方法相同
     * @param args 方法的参数
     * */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return AOPProxyUtils.applyAdvices(target, method, args, matchAdvisors, proxy, applicationContext);
    }
}
