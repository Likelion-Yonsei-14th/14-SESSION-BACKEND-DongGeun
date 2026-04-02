package com.example.demo.config;

// import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.discount.DiscountPolicy;
import com.example.demo.service.OrderService;

public class AppConfig {
    
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();  // Here : select the type of discount policy
    }

    public OrderService orderService() {
        return new OrderService(discountPolicy());
    }
}
