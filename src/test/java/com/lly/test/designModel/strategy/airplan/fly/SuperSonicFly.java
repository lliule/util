package com.lly.test.designModel.strategy.airplan.fly;

public class SuperSonicFly implements FlyType {
    @Override
    public void fly() {
        System.out.println("超音速飞行");
    }
}
