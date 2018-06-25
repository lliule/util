package com.lly.test.designModel.strategy;

/**
 * vip 会员 算法实现
 */
public class VipDiscount implements AbstractDiscount {
    @Override
    public double discount(double price) {
        System.out.println("vip 打折算法实现");
        System.out.println("增加积分");
        return price * 0.5;
    }
}
