package com.CY.AOP.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

//单例bean
public abstract class CYAbstractCreator implements BeanPostProcessor,InstantiationAwareBeanPostProcessor,ApplicationContextAware {

    private ApplicationContext applicationContext;


    /**
     * 实例化之后调用的方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    /**
     * @param bean 当前正在创建的bean
     * @param beanName 当前正常创建的beanName
     * @return 处理过的bean
     * @throws BeansException
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 我们容器中需要使用ioc底层的beanFactory 所以实现BeanFactoryAware接口
     * @param applicationContext bean工厂
     * @throws BeansException
     */


    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
