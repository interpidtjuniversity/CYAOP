package com.CY.AOP.proxy;


import com.CY.AOP.advisor.CYAdvisor;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class CYDefaultAopProxyFactory implements CYProxyFactory {



    public CYAOPProxy createAopProxy(Object bean, String beanName, List<CYAdvisor> matchAdvisors, ApplicationContext applicationContext) throws Throwable {

        return new CYJdkDynamicProxy(beanName, bean, matchAdvisors, applicationContext);
    }

}
