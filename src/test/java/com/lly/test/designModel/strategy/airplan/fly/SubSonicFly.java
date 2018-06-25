package com.lly.test.designModel.strategy.airplan.fly;

/**
 * 亚音速飞行
 */
public class SubSonicFly implements FlyType{
    @Override
    public void fly() {
        System.out.println("亚音速飞行");
    }
}
