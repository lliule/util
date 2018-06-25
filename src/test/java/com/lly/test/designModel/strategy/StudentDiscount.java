package com.lly.test.designModel.strategy;

/**
 * 学生票的算法实现
 */
public class StudentDiscount implements AbstractDiscount {
    @Override
    public double discount(double price) {
        System.out.println("学生票 0.8  折");
        return price * 0.8;
    }
}
