package com.CY.AOP.core;


import com.CY.AOP.advisor.CYAdvisor;
import lombok.Data;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 找到切面的类,然后把切面类中的切面方法
 * 构建为成增强器(在代理对象中需要织入的对象)
 */
@Data
public class CYAspectAdvisorBuilder {
    //保存容器中所有类型为CYAspect的切面类bean
    private List<String> cYAspectBeanNames = new ArrayList<String>();

    //保存每一个切面类的 通知器
    private final Map<String, List<CYAdvisor>> advisorsCache = new ConcurrentHashMap<String, List<CYAdvisor>>();

    //运行时上下文
    private ApplicationContext applicationContext;

    /**
     * 把切通知转为通知者
     * @return
     */
    public List<CYAdvisor> transAdviceToAdvisor() throws NoSuchMethodException {

        List<CYAdvisor> cYAdvisors = new ArrayList<CYAdvisor>();

        if(cYAspectBeanNames.isEmpty()) {
            //第一步:去容器中查找所有的类名称
            List<String> beanNameList = Arrays.asList(getApplicationContext().getBeanNamesForType(Object.class));

            //获取list<beanName>的迭代器
            Iterator<String> nameIterator = beanNameList.iterator();

            //开始迭代
            while (nameIterator.hasNext()) {
                String beanName = nameIterator.next();
                //根据beanName获取 class对象
                Class<?> clazz = applicationContext.getType(beanName);
                //判断class对象是不是切面对象
                if(CYAspectjFinder.isCYAspect(clazz)){
                    cYAspectBeanNames.add(beanName);
                    //把我们的切面转为增强器
                    List<CYAdvisor> cyAdvisorList =CYAspectjFinder.getAdvisor(clazz,applicationContext);
                    //加入到缓存中
                    advisorsCache.put(beanName,cyAdvisorList);
                    cYAdvisors.addAll(cyAdvisorList);
                }
            }
        }else{
            //缓存中有 直接从缓存中获取
            for(String cYAspectBeanName:cYAspectBeanNames) {
                cYAdvisors.addAll(advisorsCache.get(cYAspectBeanName));
            }
        }
        return cYAdvisors;
    }
}
