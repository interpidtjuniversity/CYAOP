package com.CY.AOP.pointcut;


import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;


//切点表达式类
public class CYAspectExpPointCut implements CYPointCut {

    /**
     * aspect 中的切点解析器
     */
    private static PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    /**
     * 切点表达式 "execution(...)"字符串
     */
    private String expression;

    /**
     * 切点表达式对象
     */
    private PointcutExpression pointcutExpression;

    public CYAspectExpPointCut(String expression) {
        //保存切点表达式
        this.expression = expression;
        //根据切点表达式字符串构建切点表达式对象
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }


    /**
     * 匹配目标类
     * @param targetClass 正在创建的类
     * @return true|false
     */
    public boolean matchClass(Class<?> targetClass) {
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    /**
     * 匹配目标方法
     * @param method 当前正在执行的类上的方法
     * @param targetClass 正在调用的类
     * @return true|false
     */
    public boolean matchMethod(Method method, Class<?> targetClass) {
        ShadowMatch sm = pointcutExpression.matchesMethodExecution(method);
        return sm.alwaysMatches();
    }

}
