package test;

import com.CY.AOP.annotation.EnableCYAOP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCYAOP
public class MainConfig {

    @Bean
    public Calculate calculate() {
        return new CYCalculate();
    }

    @Bean
    public CYLogAspect cYLogAspect() {
        return new CYLogAspect();
    }
}
