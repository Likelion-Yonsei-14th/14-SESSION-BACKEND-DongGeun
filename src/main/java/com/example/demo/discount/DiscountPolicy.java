package com.example.demo.discount;

import com.example.demo.member.Member;

public interface DiscountPolicy {
    double discount(Member member, double price);
}
