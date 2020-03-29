package com.CY.AOP.enumaration;


public enum CYAdviceExecuteOrderEnum {

    BEFORE_ADVICE(3,"前置通知"),

    AFTER_ADVICE(2,"后置通知"),

    RETURING_ADVICE(1,"返回通知"),

    THROWING_ADVICE(0,"异常通知")
    ;

    public Integer getExeOrder() {
        return exeOrder;
    }

    private Integer exeOrder;

    private String exeAdvice;

    CYAdviceExecuteOrderEnum(Integer exeOrder, String exeAdvice) {
        this.exeOrder = exeOrder;
        this.exeAdvice = exeAdvice;
    }
}
