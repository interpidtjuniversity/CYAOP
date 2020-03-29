package com.CY.AOP.joinpoint.invocation;

import com.CY.AOP.joinpoint.CYJoinPoint;


public interface CYInvocation extends CYJoinPoint {
    Object[] getArguments();
}
