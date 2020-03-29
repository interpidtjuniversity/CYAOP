package com.CY.AOP.core;

import com.CY.AOP.advice.CYMethodAfterAdvice;
import com.CY.AOP.advice.CYMethodAfterReturningAdvice;
import com.CY.AOP.advice.CYMethodBeforeAdvice;
import com.CY.AOP.advice.CYMethodThrowingAdvice;
import com.CY.AOP.advisor.CYAdvisor;
import com.CY.AOP.advisor.CYAspectPointcutAdvisor;
import com.CY.AOP.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CYAspectjFinder {
    private static final Class<?>[] ASPECTJ_ANNOTATION_CLASSES = new Class<?>[] {
            CYAfter.class, CYAfterThrowing.class, CYAfterReturning.class, CYBefore.class};

    /**
     * 根据class类型判断是不是切面类
     * @param clazz
     * @return true|false
     */
    public static boolean isCYAspect(Class<?> clazz) {
        //做非空判断
        if(clazz == null ) {
            return false;
        }
        //获取class对象上是否标注了AngleAspect注解
        CYAspect cYAspect = clazz.getAnnotation(CYAspect.class);
        if(cYAspect == null) {
            return false;
        }
        log.info("已经探测到CY切面类:{}",clazz.getCanonicalName());
        return true;
    }

    /**
     * 从切面信息中找出增强器
     * @param clazz 切面类
     * @return List<AngleAdvisor>
     */
    public static List<CYAdvisor> getAdvisor(Class<?> clazz, ApplicationContext applicationContext) throws NoSuchMethodException {
        List<CYAdvisor> AdvisorList = new ArrayList<CYAdvisor>();
        //去切面类中找 注解
        List<Method> annoMethods = getAnnotationMethod(clazz);

        //没有切面方法 返回空集合
        if(annoMethods.isEmpty()) {
            return AdvisorList;
        }

        //获取切点表达式
        String expression = parsePointCutExp(clazz);

        //解析出方法上的注解 构建成一个个的增强器
        for(Method method:annoMethods) {
            if(method.getAnnotation(CYBefore.class)!=null) {
                //前置通知
                CYMethodBeforeAdvice cYMethodBeforeAdvice = new CYMethodBeforeAdvice(method,clazz,applicationContext);
                //前置通知者(包括了通知+切点)
                CYAspectPointcutAdvisor cYAspectPointcutAdvisor = new CYAspectPointcutAdvisor(cYMethodBeforeAdvice,expression);
                cYAspectPointcutAdvisor.setCYAspectAdviceMethod(method.getName());
                AdvisorList.add(cYAspectPointcutAdvisor);
            }
            if(method.getAnnotation(CYAfter.class)!=null) {
                //创建后置通知切面
                CYMethodAfterAdvice cYMethodAfterAdvice = new CYMethodAfterAdvice(method,clazz,applicationContext);
                CYAspectPointcutAdvisor cYAspectPointcutAdvisor = new CYAspectPointcutAdvisor(cYMethodAfterAdvice,expression);
                cYAspectPointcutAdvisor.setCYAspectAdviceMethod(method.getName());
                AdvisorList.add(cYAspectPointcutAdvisor);
            }
            if(method.getAnnotation(CYAfterReturning.class)!=null) {
                //创建返回通知切面
                CYMethodAfterReturningAdvice cYMethodAfterReturningAdvice = new CYMethodAfterReturningAdvice(method,clazz,applicationContext);
                CYAspectPointcutAdvisor cYAspectPointcutAdvisor = new CYAspectPointcutAdvisor(cYMethodAfterReturningAdvice,expression);
                cYAspectPointcutAdvisor.setCYAspectAdviceMethod(method.getName());
                AdvisorList.add(cYAspectPointcutAdvisor);
            }
            if(method.getAnnotation(CYAfterThrowing.class)!=null) {
                //异常通知
                CYMethodThrowingAdvice cYMethodThrowingAdvice = new CYMethodThrowingAdvice(method,clazz,applicationContext);
                CYAspectPointcutAdvisor cYAspectPointcutAdvisor = new CYAspectPointcutAdvisor(cYMethodThrowingAdvice,expression);
                cYAspectPointcutAdvisor.setCYAspectAdviceMethod(method.getName());
                AdvisorList.add(cYAspectPointcutAdvisor);
            }
        }
        return AdvisorList;
    }


    /**
     * 获取切面类标注了注解的方法
     * @param clazz
     * @return
     */
    private static List<Method> getAnnotationMethod(Class<?> clazz) {
        List<Method> annotationMethods = new ArrayList<Method>();
        Method[] allMethod = clazz.getDeclaredMethods();

        //查找带有注解的方法
        for(Method method:allMethod) {
            if(method.getAnnotation(CYBefore.class)!=null||method.getAnnotation(CYAfter.class)!=null
                    ||method.getAnnotation(CYAfterReturning.class)!=null||method.getAnnotation(CYAfterThrowing.class)!=null) {
                annotationMethods.add(method);
            }
        }
        return annotationMethods;
    }


    /**
     * 从给定注解中解析出表达式
     * @return String类型切点字符串
     */
    private static String parsePointCutExp(Class<?> clazz)  {
        Method []methods = clazz.getDeclaredMethods();
        //每一个方法的切点表达式可能不一样,这里先考虑设置成一样的切点表达式,所以只返回第一个
        for(Method method :methods) {
            CYPointCut cYPointCut = method.getAnnotation(CYPointCut.class);
            if(cYPointCut!=null) {
                return cYPointCut.value();
            }
        }
        log.warn("CYPointCut中的切点表达式不存在");
        throw new RuntimeException("CYPointCut中的切点表达式不存在");
    }
}
