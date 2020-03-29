package com.CY.AOP.proxy;

import com.CY.AOP.advisor.CYAdvisor;
import com.CY.AOP.advisor.CYAspectPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.OrderComparator;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AOPProxyUtils {

	/**
	 * 对方法应用advices增强
	 * 
	 * @param target 被代理的对象
	 * @param method
	 * @param args
	 * @param matchAdvisors
	 * @param proxy 代理对象
	 * @param applicationContext
	 * @return
	 * @throws Throwable
	 */
	public static Object applyAdvices(Object target, Method method, Object[] args, List<CYAdvisor> matchAdvisors,
			Object proxy, ApplicationContext applicationContext) throws Throwable {
		// 1、获取要对当前方法进行增强的advice             这里发生了类型转换
		List<Object> advices = AOPProxyUtils.getShouldApplyAdvices(target.getClass(), method, matchAdvisors,
				applicationContext);

		//对advices的列表进行排序
		OrderComparator.sort(advices);

		// 2、如有增强的advice，责任链式增强执行
		if (CollectionUtils.isEmpty(advices)) {
			return method.invoke(target, args);             //方法没有没增强(没有CYAdvice)  直接调用
		} else {
			// 责任链式执行增强 方法级别
			AOPAdviceChainInvocation chain = new AOPAdviceChainInvocation(proxy, target, method, args, advices);
			return chain.proceed();
		}
	}

	/**
	 * 获取与方法匹配的切面的advices 筛选
	 * 
	 * @param beanClass
	 * @param method
	 * @param matchAdvisors 类级别的CYAdvisor
	 * @param applicationContext
	 * @return 方法级别的CYAdvice
	 * @throws Exception
	 */
	public static List<Object> getShouldApplyAdvices(Class<?> beanClass, Method method, List<CYAdvisor> matchAdvisors,
			ApplicationContext applicationContext) throws Throwable {
		if (CollectionUtils.isEmpty(matchAdvisors)) {
			return null;
		}
		List<Object> advices = new ArrayList<Object>();
		method = beanClass.getDeclaredMethod(method.getName(),method.getParameterTypes());
		for (Object cYAdvisor : matchAdvisors) {
			if(((CYAspectPointcutAdvisor) cYAdvisor).getCYPointCut().matchMethod(method,beanClass)) {
				advices.add(((CYAspectPointcutAdvisor) cYAdvisor).getCYAdvice());
			}
		}
		return advices;
	}

}
