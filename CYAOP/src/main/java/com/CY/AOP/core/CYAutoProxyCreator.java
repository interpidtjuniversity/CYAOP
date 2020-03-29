package com.CY.AOP.core;

import com.CY.AOP.advisor.CYAdvisor;
import com.CY.AOP.advisor.CYAspectPointcutAdvisor;
import com.CY.AOP.proxy.CYDefaultAopProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class CYAutoProxyCreator extends CYAbstractCreator  {

    private CYAspectAdvisorBuilder cYAspectAdvisorBuilder;


    /**
     * 在实例化前查找容器中的切面信息
     * @param beanClass 当前正常创建bean的class对象
     * @param beanName 当前正在创建的beanName
     * @return
     * @throws BeansException
     */
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        try {
            cYAspectAdvisorBuilder.transAdviceToAdvisor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生存代理对象
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<CYAdvisor> cYAdvices =  getMatchedAdvice(bean.getClass());
        if(!cYAdvices.isEmpty()) {                //被代理类的bean是在这里被  ***替换***  的
            //创建代理对象
            try {
                bean = this.createProxy(bean, beanName, cYAdvices);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return bean;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        //初始化切面构造器
        cYAspectAdvisorBuilder = new CYAspectAdvisorBuilder();
        cYAspectAdvisorBuilder.setApplicationContext(applicationContext);
    }

    /**
     * 包装我们的bean对象 获取一个class的增强器
     * @param clazz 需要包装的bean的class
     * @return 返回一个class的所有通知者(是类级别的不是方法级别的)
     */
    public List<CYAdvisor> getMatchedAdvice(Class clazz){
        if(cYAspectAdvisorBuilder.getAdvisorsCache().isEmpty()) {
            return null;
        }

        List<Method> methods =getAllMethodForClass(clazz);

        // 存放匹配的Advisor的list
        List<CYAdvisor> matchAdvisors = new ArrayList<CYAdvisor>();

        //遍历所有的切面类
        for(String aspectName : cYAspectAdvisorBuilder.getAdvisorsCache().keySet()) {
            List<CYAdvisor> listMap = cYAspectAdvisorBuilder.getAdvisorsCache().get(aspectName);
            for(CYAdvisor cYAdvisor:listMap) {
                if(((CYAspectPointcutAdvisor)cYAdvisor).getCYPointCut().matchClass(clazz)){       //通知者的切点表达式是否和当前bean包路径一致
                    matchAdvisors.add(cYAdvisor);
                }
            }
        }
        return matchAdvisors;
    }

    private List<Method> getAllMethodForClass(Class<?> beanClass) {
        List<Method> allMethods = new LinkedList<Method>();
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>(ClassUtils.getAllInterfacesForClassAsSet(beanClass));
        classes.add(beanClass);
        for (Class<?> clazz : classes) {
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
            for (Method m : methods) {
                allMethods.add(m);
            }
        }
        return allMethods;
    }

    /**
     * @param matchAdvisors 类级别的CYAdvisor
     * */
    private Object createProxy(Object bean, String beanName, List<CYAdvisor> matchAdvisors) throws Throwable {
        // 通过AopProxyFactory工厂去完成选择、和创建代理对象的工作。
        return new CYDefaultAopProxyFactory().createAopProxy(bean,beanName,matchAdvisors,getApplicationContext()).getProxy();
    }
}
