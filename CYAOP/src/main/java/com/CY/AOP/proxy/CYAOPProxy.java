package com.CY.AOP.proxy;

public interface CYAOPProxy {

    /**
     * 获取代理对象
     * @return
     */
    Object getProxy();

    /**
     * 获取代理对象
     * @return
     */
    Object getProxy(ClassLoader classLoader);
}
