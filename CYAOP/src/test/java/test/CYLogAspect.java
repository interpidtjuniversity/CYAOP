package test;

import com.CY.AOP.annotation.*;
import com.CY.AOP.joinpoint.CYMethodJoinPoint;

import java.util.Arrays;

@CYAspect
public class CYLogAspect {

    @CYPointCut("execution(* test.CYCalculate.*(..))")
    public void pointCut(){};

    @CYBefore(value = "pointCut()")
    public void methodBefore(CYMethodJoinPoint joinPoint){

        String methodName = joinPoint.getTargetMethod();
        System.out.println("执行目标方法【"+methodName+"】之前执行<前置通知>,入参"+ Arrays.asList(joinPoint.getArgs()));
    }

    @CYAfter(value = "pointCut()")
    public void methodAfter(CYMethodJoinPoint joinPoint) {
        String methodName = joinPoint.getTargetMethod();
        System.out.println("执行目标方法【"+methodName+"】之前执行<后置通知>,入参"+Arrays.asList(joinPoint.getArgs()));
    }

    @CYAfterReturning(value = "pointCut()")
    public void methodReturning(CYMethodJoinPoint joinPoint ) {
        String methodName = joinPoint.getTargetMethod();
        System.out.println("执行目标方法【"+methodName+"】之前执行<返回通知>,入参"+Arrays.asList(joinPoint.getArgs()));
    }

    @CYAfterThrowing(value = "pointCut()")
    public void methodAfterThrowing(CYMethodJoinPoint joinPoint) {
        String methodName = joinPoint.getTargetMethod();
        System.out.println("执行目标方法【"+methodName+"】之前执行<异常通知>,入参"+Arrays.asList(joinPoint.getArgs()));
    }
}
