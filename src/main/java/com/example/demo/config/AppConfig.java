package com.example.demo.config;

// import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.discount.DiscountPolicy;
import com.example.demo.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
public class AppConfig {

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();  // Here : select the type of discount policy
    }

    public OrderService orderService() {
        return new OrderService(discountPolicy());
    }
}
