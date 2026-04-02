package com.example.demo.discount;

import com.example.demo.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {
    
    @Override
    public double discount(Member member, double price) {
        if (member.getGrade().equals("VIP")) {
            return 1000;
        }
        return 0;
    }
}
