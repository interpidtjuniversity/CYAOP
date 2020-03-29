package com.CY.AOP.joinpoint.invocation;

import java.lang.reflect.Method;

public interface CYMethodInvocation extends CYInvocation{
    Method getMethod();
}
