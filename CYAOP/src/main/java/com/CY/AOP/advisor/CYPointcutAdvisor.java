package com.CY.AOP.advisor;

import com.CY.AOP.pointcut.CYPointCut;

public interface CYPointcutAdvisor extends CYAdvisor{
    /**
     * 获取切点
     * @return CYPointCut
     */
    CYPointCut getCYPointCut();
}
