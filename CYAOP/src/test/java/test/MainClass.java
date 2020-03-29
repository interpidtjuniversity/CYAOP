package test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        Calculate calculate = (Calculate) ctx.getBean("calculate");
        int retVal = calculate.div(15,15);
        System.out.println("运算结果是:"+retVal);
    }
}
