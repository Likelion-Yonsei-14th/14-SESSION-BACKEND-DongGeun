package com.example.demo.service;

import com.example.demo.discount.DiscountPolicy;
import com.example.demo.member.Member;

public class OrderService {
    private final DiscountPolicy discountPolicy;

    public OrderService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public double createOrder(Member member, double price) {
        return price - discountPolicy.discount(member, price);
    }
}
