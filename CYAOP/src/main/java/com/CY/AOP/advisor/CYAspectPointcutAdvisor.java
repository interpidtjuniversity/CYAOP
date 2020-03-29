package com.CY.AOP.advisor;



import com.CY.AOP.advice.CYAdvice;
import com.CY.AOP.pointcut.CYAspectExpPointCut;
import com.CY.AOP.pointcut.CYPointCut;
import lombok.Data;

@Data
public class CYAspectPointcutAdvisor implements CYPointcutAdvisor{

    //用户保存通知
    private CYAdvice cYAdvice;

    //切点表达式
    private String expression;

    //定义切面中的方法(全路径)
    private String cYAspectAdviceMethod;

    //基于aspectj的切点表达式对象
    private CYAspectExpPointCut cYAspectExpPoint;

    //简单方法名称
    private String methodName;

    //当前需要执行的切面对象
    private Object currentAspect;


    public CYAspectPointcutAdvisor(CYAdvice cYAdvice, String expression) {
        this.cYAdvice = cYAdvice;
        this.expression = expression;
        this.cYAspectExpPoint = new CYAspectExpPointCut(this.expression);
    }



    public CYPointCut getCYPointCut() {
        return this.cYAspectExpPoint;
    }

    public CYAdvice getCYAdvice() {
        return this.cYAdvice;
    }
}
