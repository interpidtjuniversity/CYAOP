package com.CY.AOP.advisor;

import com.CY.AOP.advice.CYAdvice;

public interface CYAdvisor {
    /**
     * 获取所在通知对象
     * @return
     */
    CYAdvice getCYAdvice();
}
