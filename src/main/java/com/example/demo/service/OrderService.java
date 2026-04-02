package com.example.demo.service;

// import com.example.demo.discount.FixDiscountPolicy;
import com.example.demo.discount.RateDiscountPolicy;
import com.example.demo.discount.DiscountPolicy;
import com.example.demo.member.Member;

public class OrderService {
    // private DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private DiscountPolicy discountPolicy = new RateDiscountPolicy();

    public double createOrder(Member member, double price) {
        return price - discountPolicy.discount(member, price);
    }
}
